package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing by a negative divisor should negate the sign of the result: 12 / -3 = -4
        Weeks twelveWeeks = Weeks.of(12);
        Weeks result = twelveWeeks.dividedBy(-3);
        assertEquals(Weeks.of(-4), result);
    }
}
