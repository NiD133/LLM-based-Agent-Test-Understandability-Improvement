package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing by a negative divisor should negate the result: 12 / -3 = -4
        Years twelveYears = Years.of(12);
        Years result = twelveYears.dividedBy(-3);
        assertEquals(Years.of(-4), result);
    }
}
