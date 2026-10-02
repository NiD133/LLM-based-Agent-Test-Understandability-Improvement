package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_dividedBy_negate {

    // Dividing by a negative divisor should negate the sign of the result:
    // 12 / -3 = -4
    @Test
    public void test_dividedBy_negate() {
        Seconds test12 = Seconds.of(12);
        assertEquals(Seconds.of(-4), test12.dividedBy(-3));
    }
}
