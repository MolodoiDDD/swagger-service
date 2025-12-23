package com.example.swagger_service;

import com.example.swagger_service.exception.CitizenAlreadyExitsException;
import com.example.swagger_service.model.Citizen;
import com.example.swagger_service.repository.CitizenRepository;
import com.example.swagger_service.service.CitizenService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CitizenServiceTest {

    @Mock
    CitizenRepository citizenRepository;

    @InjectMocks
    CitizenService citizenService;

    @Test
    void create_throwsWhenCitizenExists()
    {
        Citizen citizen = new Citizen();
        citizen.setFirstName("Иван");
        citizen.setLastName("Иванов");
        citizen.setMiddleName("Иванович");
        citizen.setBirthDate(LocalDate.of(2000, 1, 1));

        when(citizenRepository.existsByLastNameAndFirstNameAndMiddleNameAndBirthDate(citizen.getLastName(),
                citizen.getFirstName(),
                citizen.getMiddleName(),
                citizen.getBirthDate())).thenReturn(true);

        Executable action = new Executable()
        {
            @Override
            public void execute()
            {
                citizenService.create(citizen);
            }
        };

        assertThrows(CitizenAlreadyExitsException.class, action);
        verify(citizenRepository, never()).save(any());
    }

    @Test
    void getById_returnsCitizen()
    {
        Citizen citizen = new Citizen();
        citizen.setId(1L);

        when(citizenRepository.findById(1L)).thenReturn(Optional.of(citizen));

        Citizen result = citizenService.getById(1L);

        assertEquals(1L, result.getId());
    }
}
