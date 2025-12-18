package com.example.swagger_service.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "citizens")
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Поле 'имя' не может быть пустым")
    @Size(min = 1, max = 50, message = "Поле 'Имя' должно быть от 2 до 50 символов")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Поле 'Фамилия' не может быть пустым")
    @Size(min = 1, max = 50, message = "Поле 'Фамилия' должно быть от 2 до 50 символов")
    private String lastName;

    @Column(name = "middle_name")
    @Size(min = 1, max = 50, message = "Поле 'Отчество' должно быть от 2 до 50 символов")
    private String middleName;

    @Column(name = "birth_date")
    @NotNull(message = "Поле 'Дата рождения' не может быть пустым")
    @PastOrPresent(message = "Поле 'Дата рождения' не может быть в будущем")
    private LocalDate birthDate;

    @Column(name = "phone")
    @NotNull(message = "Поле 'Номер телефона' не может быть пустым")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат номера телефона")
    private String phone;

    @Column(name = "extra_phone")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Некорректный формат доп. номера телефона")
    private String extraPhone;

    @Column(name = "dul_series")
    @NotBlank(message = "Поле 'Номер паспорта' не может быть пустым")
    @Pattern(regexp = "^\\d{4}$", message = "Поле 'Номер паспорта' должно содеражать 4 цифры")
    private String dulSeries;

    @Column(name = "dul_number")
    @NotBlank(message = "Поле 'Серия паспорта' не может быть пустым")
    @Pattern(regexp = "^\\d{6}$", message = "Поле 'Серия паспорта' должно содеражать 6 цифры")
    private String dulNumber;

}
