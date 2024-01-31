package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "lesson")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date_event")
    private LocalDate dateEvent;

    @NotBlank
    @Size(max = 1000)
    private String topic;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private SubjectGroup subjectGroup;
}
