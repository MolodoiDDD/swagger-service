package com.example.swagger_service.controller;

import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.service.CitizenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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
    public Citizen getById(@PathVariable Long id)
    {
        return citizenService.getById(id);
    }

    @PostMapping("/citizens")
    @ResponseStatus(HttpStatus.CREATED)
    public Citizen create(@Valid @RequestBody Citizen citizen)
    {
        return citizenService.create(citizen);
    }

    @DeleteMapping("/citizens/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id)
    {
        citizenService.delete(id);
    }

    @PutMapping("/citizens/{id}")
    public Citizen update(@PathVariable Long id,
                          @Valid @RequestBody Citizen citizen)
    {
        return citizenService.update(id, citizen);
    }
}
