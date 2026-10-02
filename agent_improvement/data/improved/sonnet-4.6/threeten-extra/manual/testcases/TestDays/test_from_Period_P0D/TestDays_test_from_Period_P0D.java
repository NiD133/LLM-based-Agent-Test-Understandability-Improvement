package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_Period_P0D {

    @Test
    public void test_from_Period_P0D() {
        // Days.from should convert a zero-day Period to Days.of(0)
        Days expected = Days.of(0);
        Days actual = Days.from(Period.ofDays(0));
        assertEquals(expected, actual);
    }
}
