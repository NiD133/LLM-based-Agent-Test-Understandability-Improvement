package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_toDuration {

    @Test
    public void test_toDuration_zero() {
        assertEquals(Duration.ZERO, Seconds.of(0).toDuration());
    }

    @Test
    public void test_toDuration_positive() {
        for (int i = 1; i < 20; i++) {
            assertEquals(Duration.ofSeconds(i), Seconds.of(i).toDuration());
        }
    }

    @Test
    public void test_toDuration_negative() {
        for (int i = -20; i < 0; i++) {
            assertEquals(Duration.ofSeconds(i), Seconds.of(i).toDuration());
        }
    }
}
