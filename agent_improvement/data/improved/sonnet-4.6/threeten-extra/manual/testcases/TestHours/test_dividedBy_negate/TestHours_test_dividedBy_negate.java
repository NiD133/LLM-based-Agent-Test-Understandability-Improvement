package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_dividedBy_negate {

    // Dividing a positive amount by a negative divisor should negate the result.
    @Test
    public void test_dividedBy_negate() {
        Hours test12 = Hours.of(12);
        assertEquals(Hours.of(-4), test12.dividedBy(-3));
    }
}
