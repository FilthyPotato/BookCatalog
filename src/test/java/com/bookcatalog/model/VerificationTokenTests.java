package com.bookcatalog.model;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class VerificationTokenTests {
    private VerificationToken verificationToken;
    private Clock fixedClock;

    @Before
    public void setUp() {
        fixedClock = Clock.fixed(Instant.ofEpochSecond(0), ZoneId.systemDefault());
        verificationToken = new VerificationToken(fixedClock);
    }

    @Test
    public void whenCreatedThenExpiryDateIsNowPlus24Hours(){
        LocalDateTime nowPlus24Hours = LocalDateTime.now(fixedClock).plusHours(24);
        assertThat(verificationToken.getExpiryDate(), is(nowPlus24Hours));
    }

    @Test
    public void whenExpiryDateIsBeforeNowThenExpired() {
        verificationToken.setExpiryDate(LocalDateTime.now(fixedClock).minusDays(1));
        assertTrue(verificationToken.hasExpired());
    }

    @Test
    public void whenExpiryDateIsAfterNowThenNotExpired(){
        verificationToken.setExpiryDate(LocalDateTime.now(fixedClock).plusDays(1));
        assertFalse(verificationToken.hasExpired());
    }
}
