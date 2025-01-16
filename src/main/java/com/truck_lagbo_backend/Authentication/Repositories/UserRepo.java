package com.truck_lagbo_backend.Authentication.Repositories;

import com.truck_lagbo_backend.Authentication.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long userId);
    Optional<User> findByResetToken(String resetToken);
}
