package com.example.demo.attendence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id ;

    @Column(nullable = false)
    private String token ;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt ;

    private LocalDateTime confirmedAt ;

    @ManyToOne
    @JoinColumn(
            nullable = false ,
            name = "app_user_id"
                )
    private User user;

    public ConfirmationToken(
            String token,
            LocalDateTime createAt,
            LocalDateTime expiresAt,
            User user) {
        this.token = token;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
