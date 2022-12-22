package com.example.demo.attendence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private Integer type ;

    @Column
    private String email ;

    @Column
    private String phone ;

    @ManyToOne
//    @JoinColumn(name = "teams")
    private Long team_id ;
}
