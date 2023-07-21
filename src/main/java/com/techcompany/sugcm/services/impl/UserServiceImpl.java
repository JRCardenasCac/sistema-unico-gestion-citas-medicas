package com.techcompany.sugcm.services.impl;

import com.techcompany.sugcm.models.dto.UserDto;
import com.techcompany.sugcm.models.entity.*;
import com.techcompany.sugcm.models.requests.DoctorRequest;
import com.techcompany.sugcm.repositories.*;
import com.techcompany.sugcm.services.UserService;
import com.techcompany.sugcm.util.enums.TokenType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.techcompany.sugcm.util.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final DoctorSpecialtyRepository doctorSpecialtyRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final SpecialtyRepository specialtyRepository;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto saveAdministrator(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("El " + existingUser.get().getProfile() + " ya se encuentra registrado.");
        } else {
            Optional<Role> role = roleRepository.findByName(user.getProfile());
            if (role.isPresent()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                User savedUser = userRepository.save(user);
                UserRole userRole = new UserRole();
                userRole.setUser(savedUser);
                userRole.setRole(role.get());
                userRoleRepository.save(userRole);

                var jwtToken = jwtService.generateToken(user);
                jwtService.generateRefreshToken(user);
                saveUserToken(savedUser, jwtToken);

                return modelMapper.map(savedUser, UserDto.class);
            } else {
                throw new Exception("El rol del usuario es inválido.");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto saveDoctor(DoctorRequest doctorRequest) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(doctorRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new Exception("El " + existingUser.get().getProfile() + " ya se encuentra registrado.");
        } else {
            Optional<Role> role = roleRepository.findByName(doctorRequest.getProfile());
            if (role.isPresent()) {
                doctorRequest.setPassword(passwordEncoder.encode(doctorRequest.getPassword()));
                User savedUser = userRepository.save(modelMapper.map(doctorRequest, User.class));
                UserRole userRole = new UserRole();
                userRole.setUser(savedUser);
                userRole.setRole(role.get());
                userRoleRepository.save(userRole);

                var doctor = doctorRepository.save(Doctor.builder().user(savedUser).build());

                if (doctorRequest.getSpecialties().isEmpty()) {
                    throw new Exception("El doctor debe estar asociado a una espcialidad medica");
                } else {
                    List<DoctorSpecialty> doctorSpecialties = new ArrayList<>();
                    for (Specialty specialty : doctorRequest.getSpecialties()) {
                        if (specialtyRepository.findByName(specialty.getName()).isEmpty()) {
                            throw new Exception("La especialidad enviada no esta registrada");
                        }
                        DoctorSpecialty doctorSpecialty = DoctorSpecialty.builder()
                                .doctor(doctor)
                                .specialty(specialty)
                                .build();
                        doctorSpecialties.add(doctorSpecialty);
                    }
                    doctorSpecialtyRepository.saveAll(doctorSpecialties);
                }
                if (doctorRequest.getDoctorSchedules().isEmpty()) {
                    List<DoctorSchedule> doctorSchedules = new ArrayList<>();
                    for (DayOfWeek dayOfWeek : DAY_OF_WEEK) {
                        DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                                .doctor(doctor)
                                .dayOfWeek(dayOfWeek)
                                .startTime(START_TIME_WORK)
                                .endTime(END_TIME_WORK)
                                .build();
                        doctorSchedules.add(doctorSchedule);
                    }
                    doctorScheduleRepository.saveAll(doctorSchedules);
                } else {
                    List<DoctorSchedule> doctorSchedules = new ArrayList<>();
                    for (DoctorSchedule schedule : doctorRequest.getDoctorSchedules()) {
                        DoctorSchedule doctorSchedule = DoctorSchedule.builder()
                                .doctor(doctor)
                                .dayOfWeek(schedule.getDayOfWeek())
                                .startTime(schedule.getStartTime())
                                .endTime(schedule.getEndTime())
                                .build();
                        doctorSchedules.add(doctorSchedule);
                    }
                    doctorScheduleRepository.saveAll(doctorSchedules);
                }

                var jwtToken = jwtService.generateToken(savedUser);
                jwtService.generateRefreshToken(savedUser);
                saveUserToken(savedUser, jwtToken);

                return modelMapper.map(savedUser, UserDto.class);
            } else {
                throw new Exception("El rol del usuario es inválido.");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
