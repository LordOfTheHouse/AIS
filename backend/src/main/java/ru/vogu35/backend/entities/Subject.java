package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vogu35.backend.entities.enums.ETypeSubject;

@Table(name = "subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    @Column(name = "type_subject")
    @Enumerated(EnumType.STRING)
    private ETypeSubject typeSubject;

    @NotBlank
    private Integer hours;
}
