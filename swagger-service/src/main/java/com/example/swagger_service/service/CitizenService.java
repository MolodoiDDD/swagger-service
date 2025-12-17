package com.example.swagger_service.service;

import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.repository.CitizenRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


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
                             LocalDate birthDate)
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

        citizen.setId(id);

        return citizenRepository.save(citizen);
    }


}
