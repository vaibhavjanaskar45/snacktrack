package com.snacktrack.snacktrack.repository;

import com.snacktrack.snacktrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
