package ru.vogu35.backend.services.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vogu35.backend.entities.AdminToken;
import ru.vogu35.backend.repositories.AdminTokenRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminTokenService {

    private final AdminTokenRepository adminTokenRepository;

    @Autowired
    public AdminServiceImpl(AdminTokenRepository adminTokenRepository) {
        this.adminTokenRepository = adminTokenRepository;
    }

    @Override
    public boolean save(AdminToken adminToken) {
        log.info("Добавление в базу данных токена {}", adminToken.getAccessToken());
        adminTokenRepository.save(adminToken);
        return true;
    }

    @Override
    public Optional<AdminToken> findById(Integer id) {
        log.info("Получение токена(ов) из базы данных");
        return adminTokenRepository.findById(id);
    }
}
