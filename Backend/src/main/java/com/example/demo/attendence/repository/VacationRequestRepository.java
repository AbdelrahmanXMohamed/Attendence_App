package com.example.demo.attendence.repository;

import com.example.demo.attendence.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    @Query("select r from VacationRequest r inner JOIN User u on r.user.id = u.id  and u.team.id =:teamId")
    public List<VacationRequest> getAllVacationRequestPerTeam(@Param(("teamId")) Long teamId);

}
