package com.example.demo.attendence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@ToString(exclude = "users")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;


    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;


    @OneToMany(mappedBy = "team")
    List<User> users ;
}
