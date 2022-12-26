package com.example.demo.attendence.repository;

import com.example.demo.attendence.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByManager_Id(Long manger_id);

}
