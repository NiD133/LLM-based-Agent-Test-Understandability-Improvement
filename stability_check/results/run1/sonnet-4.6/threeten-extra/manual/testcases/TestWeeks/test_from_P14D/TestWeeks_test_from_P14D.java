package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_from_P14D {

    @Test
    public void test_from_P14D() {
        // 14 days is exactly 2 weeks, so converting Period.ofDays(14) should yield Weeks.of(2)
        Weeks expected = Weeks.of(2);
        Weeks actual = Weeks.from(Period.ofDays(14));
        assertEquals(expected, actual);
    }
}
