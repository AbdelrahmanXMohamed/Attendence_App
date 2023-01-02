package com.example.demo.attendence.repository;

import com.example.demo.attendence.entity.Status;
import com.example.demo.attendence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE s.user.id = ?1 And s.day = ?2")
    Optional<Status> findByUserAndDate(Long id, LocalDate date);
}
