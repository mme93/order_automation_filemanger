package de.mameie.filemanager.security.repository;


import de.mameie.filemanager.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUsername(String username);
    List<UserEntity> findAllByCompany(String company);
}
