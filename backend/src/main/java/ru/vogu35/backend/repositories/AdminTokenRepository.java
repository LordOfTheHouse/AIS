package ru.vogu35.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vogu35.backend.entities.AdminToken;

@Repository
public interface AdminTokenRepository extends JpaRepository<AdminToken, Integer> {
}
