package ru.vogu35.backend.auth;

import ru.vogu35.backend.entities.AdminToken;

import java.util.List;
import java.util.Optional;

public interface AdminTokenService {
    /**
     * сохраняет токен в БД
     *
     * @param adminToken - токен для клиента сервиса заказов
     * @return true в случае успеха
     */
    boolean save(AdminToken adminToken);

    /**
     * Возвращает токен админа
     *
     * @return токены
     */
    Optional<AdminToken> findById(Integer id);
}
