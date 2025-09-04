package com.scaglia.controle_financeiro.repository;

import com.scaglia.controle_financeiro.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    //select * from tb_categories where profile_id = ?1
    List<CategoryEntity> findByProfileId(Long profileId);

    //select * from tb_categories where profile_id = ?1 and profile_id = ?2
    Optional<CategoryEntity> findByIdAndProfileId(Long id, Long profileId);

    //select * from tb_categories where type = ?1 and profile_id = ?2
    List<CategoryEntity> findByTypeAndProfileId(String type, Long profileId);

    Boolean existsByNameAndProfileId(String type, Long profileId);
}
