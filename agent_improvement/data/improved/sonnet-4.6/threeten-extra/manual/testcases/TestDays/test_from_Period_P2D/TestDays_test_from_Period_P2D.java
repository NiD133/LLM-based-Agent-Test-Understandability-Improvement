package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_Period_P2D {

    @Test
    public void test_from_Period_P2D() {
        // Days.from should convert a Period of 2 days into an equivalent Days instance
        Days expected = Days.of(2);
        Period twoDayPeriod = Period.ofDays(2);

        assertEquals(expected, Days.from(twoDayPeriod));
    }
}
