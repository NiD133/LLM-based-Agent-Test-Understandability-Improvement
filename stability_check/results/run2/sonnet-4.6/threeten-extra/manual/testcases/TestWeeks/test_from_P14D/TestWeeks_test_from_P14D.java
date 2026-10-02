package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P14D {

    @Test
    public void test_from_P14D() {
        // 14 days (P14D) is exactly 2 weeks, so Weeks.from should convert it without remainder
        assertEquals(Weeks.of(2), Weeks.from(Period.ofDays(14)));
    }
}
