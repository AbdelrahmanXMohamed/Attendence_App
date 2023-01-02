package com.example.demo.attendence.repository;

import com.example.demo.attendence.entity.ConfirmationToken;
import com.example.demo.attendence.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    @Query("SELECT s FROM Status s WHERE s.user.id = ?1 And s.day = ?2")
    Optional<Status> findByUserAndDate(Long id, LocalDate date);

    @Repository
    interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

        Optional<ConfirmationToken> findByToken(String token);

        @Transactional
        @Modifying
        @Query("UPDATE ConfirmationToken c " +
                "SET c.confirmedAt = ?2 " +
                "WHERE c.token = ?1")
        int updateConfirmedAt(String token,
                              LocalDateTime confirmedAt);

    }
}
