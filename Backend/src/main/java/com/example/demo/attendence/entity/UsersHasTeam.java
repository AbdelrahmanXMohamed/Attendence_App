package com.example.demo.attendence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users_has_teams")
@Data
public class UsersHasTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id", nullable = false)
    private Long id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    private User users;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teams_id", nullable = false)
    private Team teams;


}