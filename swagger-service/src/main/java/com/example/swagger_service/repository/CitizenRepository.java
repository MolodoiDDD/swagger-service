package com.example.swagger_service.repository;

import com.example.swagger_service.model.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitizenRepository extends JpaRepository<Citizen, Long> {


    @Query(value = """
    SELECT *
    FROM citizens
    WHERE (:firstName IS NULL OR first_name = :firstName)
      AND (:lastName  IS NULL OR last_name  = :lastName)
      AND (:middleName IS NULL OR middle_name = :middleName)
      AND (:birthDate IS NULL OR birth_date = COALESCE(:birthDate, birth_date))
    """,
            nativeQuery = true)
    List<Citizen> search(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            @Param("birthDate") LocalDateTime birthDate
    );


    Optional<Citizen> findByLastNameAndFirstNameAndMiddleNameAndBirthDate(String firstName,
                                                                          String lastName,
                                                                          String middleName,
                                                                          LocalDateTime birthDate);
}
