package com.example.swagger_service.controller;

import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.repository.CitizenRepository;
import com.example.swagger_service.service.CitizenService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenService){
        this.citizenService = citizenService;
    }


    @GetMapping("/citizens")
    public List<Citizen> get(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName,
                             @RequestParam(required = false) String middleName,
                             @RequestParam(required = false) LocalDate birthDate)
    {
        return citizenService.get(firstName, lastName, middleName, birthDate);
    }

    @GetMapping("/citizens/{id}")
    public ResponseEntity<Citizen> getById(@PathVariable Long id) {

        try {
            Citizen citizen = citizenService.getById(id);
            return ResponseEntity.ok(citizen);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/citizens")
    public ResponseEntity<Citizen> create(@RequestBody Citizen citizen)
    {
        try {
            Citizen saved = citizenService.create(citizen);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_EXTENDED).build();
        }
    }

    @DeleteMapping("/citizens/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            citizenService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/citizens/{id}")
    public ResponseEntity<Citizen> update(@PathVariable Long id,
                                          @RequestBody Citizen citizen)
    {
        try {
            Citizen saved = citizenService.update(id, citizen);
            return ResponseEntity.ok(saved);
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.notFound().build();
        }

    }
}
