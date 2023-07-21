package com.techcompany.sugcm.controllers;

import com.techcompany.sugcm.models.dto.DoctorScheduleDto;
import com.techcompany.sugcm.models.entity.Doctor;
import com.techcompany.sugcm.repositories.DoctorRepository;
import com.techcompany.sugcm.repositories.DoctorScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final ModelMapper modelMapper;


    @GetMapping("/{doctorId}/schedulers")
    public ResponseEntity<List<DoctorScheduleDto>> getAllDoctorScheduleBySpecialtyId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorScheduleRepository.findByDoctor(Doctor.builder().doctorId(doctorId).build()).stream()
                .map(ds -> modelMapper.map(ds, DoctorScheduleDto.class))
                .collect(Collectors.toList()));
    }
}
