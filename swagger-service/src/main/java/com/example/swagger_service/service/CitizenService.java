package com.example.swagger_service.service;

import com.example.swagger_service.exception.CitizenAlreadyExitsException;
import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.repository.CitizenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class CitizenService {

    private final CitizenRepository citizenRepository;

    public CitizenService(CitizenRepository citizenRepository)
    {
        this.citizenRepository = citizenRepository;
    }


    public List<Citizen> get(String firstName,
                             String lastName,
                             String middleName,
                             LocalDateTime birthDate)
    {
        return citizenRepository.search(firstName, lastName, middleName, birthDate);
    }

    public Citizen getById(Long id) {

        var optional = citizenRepository.findById(id);
        if (optional.isPresent())
        {
            return optional.get();
        }
        else
        {
            throw new EntityNotFoundException("Citizen not found");
        }
    }


    public Citizen create(Citizen citizen)
    {
        boolean exist = citizenRepository.findByLastNameAndFirstNameAndMiddleNameAndBirthDate(citizen.getFirstName(),
                citizen.getLastName(),
                citizen.getMiddleName(),
                citizen.getBirthDate()).isPresent();

        if (exist)
        {
            throw new CitizenAlreadyExitsException("Гражданин с таким ФИО и датой рождения уже существует");
        }

        validAge(citizen);
        citizen.setId(null);

        return citizenRepository.save(citizen);
    }


    public void delete(Long id) {
        if (!citizenRepository.existsById(id)) {
            throw new EntityNotFoundException("Citizen not found");
        }

        citizenRepository.deleteById(id);
    }


    public Citizen update(Long id, Citizen citizen)
    {
        if (!citizenRepository.existsById(id))
        {
            throw new EntityNotFoundException("Citizen not found");
        }

        validAge(citizen);
        citizen.setId(id);

        return citizenRepository.save(citizen);
    }

    private void validAge(Citizen citizen)
    {
        LocalDateTime birthDate = citizen.getBirthDate();
        if (birthDate == null)
        {
            return;
        }

        long years = ChronoUnit.YEARS.between(birthDate, LocalDateTime.now());

        if (years < 18)
        {
            throw new IllegalArgumentException("Гражданин должен быть старше 18 лет");
        }
    }


}
