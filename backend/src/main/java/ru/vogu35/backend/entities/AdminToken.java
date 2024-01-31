package ru.vogu35.backend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "admin_token")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminToken {
    @Id
    private Integer id;
    @Column(name = "access_token", length = 10000)
    private String accessToken;
    @Column(name = "token_expiration")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime tokenExpiration;
}
