package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "lessons")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @NotBlank
    private SubjectGroup subjectGroup;

    @NotBlank
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "is_present")
    private Boolean isPresent;

    private Integer mark;
}
