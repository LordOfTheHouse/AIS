package ru.vogu35.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Table(name = "subjects_groups")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotBlank
    @Column(name = "teacher_id")
    private String teacherId;

    @NotBlank
    private LocalTime start;

    @NotBlank
    private LocalTime end;

    @NotBlank
    @Column(name = "is_week_even")
    private boolean isWeekEven;

    @NotBlank
    private Integer weekday;

}
