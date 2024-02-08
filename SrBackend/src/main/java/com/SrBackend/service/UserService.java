package com.SrBackend.service;

import com.SrBackend.dto.SignUpDto;
import com.SrBackend.entity.UserEntity;
import com.SrBackend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Transactional
    public boolean signUp(SignUpDto signUpDto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(signUpDto.getUsername());

        if (userEntity.isEmpty()) {
            throw new DuplicateKeyException("중복된 아이디");
        }

        try {
            UserEntity user = UserEntity.builder()
                .username(signUpDto.getUsername())
                .password(signUpDto.getPassword())
                .nickname(signUpDto.getNickname())
                .build();

            entityManager.persist(user);
            entityManager.flush();
            entityManager.clear();

        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}

