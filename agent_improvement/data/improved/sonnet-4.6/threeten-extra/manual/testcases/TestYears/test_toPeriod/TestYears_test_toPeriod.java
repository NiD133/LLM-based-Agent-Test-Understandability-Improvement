package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestYears_test_toPeriod {

    @Test
    public void test_toPeriod() {
        // Covers negative, zero, and positive year counts
        for (int i = -20; i < 20; i++) {
            assertEquals(Period.ofYears(i), Years.of(i).toPeriod());
        }
    }
}
