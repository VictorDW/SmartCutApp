package com.smartcut.app.domain.user.repository;

import com.smartcut.app.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.cedula= :cedula")
    Optional<User> findByCedula(@Param("cedula") String cedula);
}
