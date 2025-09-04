package com.scaglia.controle_financeiro.repository;

import com.scaglia.controle_financeiro.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    // select from tb_profiles where email = ?
    Optional<ProfileEntity> findByEmail(String email);

    // select from tb_profiles where activation_token = ?
    Optional<ProfileEntity> findByActivationToken(String activationToken);
}
