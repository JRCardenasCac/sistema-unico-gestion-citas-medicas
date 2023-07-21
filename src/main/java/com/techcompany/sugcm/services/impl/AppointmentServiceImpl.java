package com.techcompany.sugcm.services.impl;

import com.techcompany.sugcm.models.dto.AppointmentDto;
import com.techcompany.sugcm.models.entity.Appointment;
import com.techcompany.sugcm.models.entity.Doctor;
import com.techcompany.sugcm.models.entity.DoctorSchedule;
import com.techcompany.sugcm.models.entity.Patient;
import com.techcompany.sugcm.repositories.AppointmentRepository;
import com.techcompany.sugcm.repositories.DoctorScheduleRepository;
import com.techcompany.sugcm.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final DoctorScheduleRepository doctorScheduleRepository;

    @Override
    public List<AppointmentDto> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAllAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatient(Patient.builder().patientId(patientId).build()).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAllAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctor(Doctor.builder().doctorId(doctorId).build()).stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AppointmentDto> getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointment -> modelMapper.map(appointment, AppointmentDto.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AppointmentDto saveAppointment(AppointmentDto appointmentDto) throws Exception {
        try {
            if (isDoctorAvailable(modelMapper.map(appointmentDto, Appointment.class))) {
                var appointment = appointmentRepository.save(modelMapper.map(appointmentDto, Appointment.class));
                return modelMapper.map(appointment, AppointmentDto.class);
            }
            throw new Exception("La fecha y el rango horario seleccionados estan ocupados.");
        } catch (Exception e) {
            throw new Exception("Error al guardar la cita: " + e.getMessage());
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    /**
     * Método para verificar si el médico está disponible en la fecha y el rango horario seleccionados.
     *
     * @param appointment
     * @return
     */
    private boolean isDoctorAvailable(Appointment appointment) {
        // Verificar si el doctor tiene un horario para el día seleccionado
        List<DoctorSchedule> doctorSchedules = doctorScheduleRepository
                .findByDoctorAndDayOfWeek(appointment.getDoctor(), appointment.getStartDateTime().getDayOfWeek());
        if (doctorSchedules.isEmpty()) {
            return false; // El doctor no trabaja en el día seleccionado
        }


        // Verificar si el horario seleccionado está dentro del rango de atención del doctor
        for (DoctorSchedule doctorSchedule : doctorSchedules) {
            LocalTime startTime = doctorSchedule.getStartTime();
            LocalTime endTime = doctorSchedule.getEndTime();
            if (appointment.getStartDateTime().toLocalTime().isBefore(startTime) ||
                    appointment.getEndDateTime().toLocalTime().isAfter(endTime)) {
                return false; // El horario seleccionado está fuera del rango de atención del doctor
            }
        }

        // Verificar si hay alguna cita existente en el rango de horas seleccionado
        List<Appointment> existingAppointments = appointmentRepository.findByDoctorAndStartDateTimeBetween(
                appointment.getDoctor(), appointment.getStartDateTime(), appointment.getEndDateTime());
        return existingAppointments.isEmpty(); // Ya hay una cita reservada en el rango de horas seleccionado
    }
}
