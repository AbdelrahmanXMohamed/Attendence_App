package com.example.demo.attendence.repository;

import com.example.demo.attendence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
