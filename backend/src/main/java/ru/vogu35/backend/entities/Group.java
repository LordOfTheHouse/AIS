package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.enums.EFormEducation;
import ru.vogu35.backend.entities.enums.EInstitute;


@Table(name = "groups")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "form_education")
    @Enumerated(EnumType.STRING)
    private EFormEducation formEducation;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private EInstitute institute;

}
