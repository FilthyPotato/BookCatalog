package com.bookcatalog.registration.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.time.Clock;
import java.time.LocalDateTime;

@Entity
@Data
public class VerificationToken {
    private static final int EXPIRATION_TIME_IN_HOURS = 24;

    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private LocalDateTime expiryDate;
    @Transient
    @Getter(AccessLevel.NONE)
    private final Clock clock;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public VerificationToken(User user, String token) {
        this(Clock.systemDefaultZone());
        this.token = token;
        this.user = user;
    }

    public VerificationToken(Clock clock) {
        this.clock = clock;
        this.expiryDate = LocalDateTime.now(clock).plusHours(EXPIRATION_TIME_IN_HOURS);
    }

    public boolean hasExpired() {
        return expiryDate.isBefore(LocalDateTime.now(clock));
    }
}
