package com.example.demo.Repo;

import com.example.demo.Entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> getUserEntitiesByUserName(String userName);
@Modifying
@Transactional
    void deleteByUserName(String name);
}
