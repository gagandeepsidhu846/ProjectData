package com.example.demo.Repo;

import com.example.demo.Entity.User;
import com.example.demo.Entity.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    @Modifying
    @Transactional
    void deleteByUserName(String name);

    @Query("SELECT u.userId AS userId, u.userName AS userName, u.userEmail AS userEmail FROM User u")
    List<UserProjection> findAllUsers();
}
