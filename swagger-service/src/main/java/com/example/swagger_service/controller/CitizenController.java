package com.example.swagger_service.controller;

import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.service.CitizenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@Slf4j
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
        log.info("method=GET endpoint=/citizens params={{firstName={}, lastName={}, middleName={}, birthDate={}}",
        firstName, lastName, middleName, birthDate);

        return citizenService.get(firstName, lastName, middleName, birthDate);
    }

    @GetMapping("/citizens/{id}")
    public Citizen getById(@PathVariable Long id)
    {
        log.info("method=GET endpoint=/citizens/{} id={}", id, id);
        return citizenService.getById(id);
    }

    @PostMapping("/citizens")
    @ResponseStatus(HttpStatus.CREATED)
    public Citizen create(@Valid @RequestBody Citizen citizen)
    {
        log.info("method=POST endpoint=/citizens/ body={}", citizen.toString());

        return citizenService.create(citizen);
    }

    @DeleteMapping("/citizens/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id)
    {
        log.info("method=DELETE endpoint=/citizens/{} id={}", id, id);
        citizenService.delete(id);
    }

    @PutMapping("/citizens/{id}")
    public Citizen update(@PathVariable Long id,
                          @Valid @RequestBody Citizen citizen)
    {
        log.info("method=PUT endpoint=/citizens/{} id={} body={}", id, id, citizen.toString());
        return citizenService.update(id, citizen);
    }
}
