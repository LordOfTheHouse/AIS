package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "students_lesson")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    @NotBlank
    private Lesson lesson;

    @NotBlank
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "is_present")
    private Boolean isPresent;

    private Integer mark;
}
