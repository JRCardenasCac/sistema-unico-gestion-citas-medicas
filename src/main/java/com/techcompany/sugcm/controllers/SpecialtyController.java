package com.techcompany.sugcm.controllers;

import com.techcompany.sugcm.models.dto.SpecialtyDto;
import com.techcompany.sugcm.models.entity.Specialty;
import com.techcompany.sugcm.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/specialties")
@RequiredArgsConstructor
public class SpecialtyController {
    private final SpecialtyRepository specialtyRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<SpecialtyDto>> getAllSpecialtys() {
        return ResponseEntity.ok(specialtyRepository.findAll().stream()
                .map(e -> modelMapper.map(e, SpecialtyDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDto> getSpecialtyById(@PathVariable Long id) {
        return specialtyRepository.findById(id)
                .map(e -> modelMapper.map(e, SpecialtyDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SpecialtyDto> createSpecialty(@RequestBody SpecialtyDto specialtyDto) {
        try {
            if (specialtyRepository.findByName(specialtyDto.getName()).isPresent()) {
                throw new Exception("La especialidad ya se encuentra registrada");
            }

            var specialty = specialtyRepository.save(modelMapper.map(specialtyDto, Specialty.class));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(modelMapper.map(specialty, SpecialtyDto.class));
        } catch (Exception e) {
            return ResponseEntity.ok(SpecialtyDto.builder().message(e.getMessage()).build());
        }
    }
}
