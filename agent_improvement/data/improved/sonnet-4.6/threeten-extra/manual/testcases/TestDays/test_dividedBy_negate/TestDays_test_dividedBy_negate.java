package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing by a negative divisor should negate and reduce the amount
        Days twelveDays = Days.of(12);
        Days result = twelveDays.dividedBy(-3);
        assertEquals(Days.of(-4), result);
    }
}
