package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        Hours fiveHours = Hours.of(5);
        // Multiplying by a negative scalar should negate and scale the amount: 5 * -3 = -15
        Hours result = fiveHours.multipliedBy(-3);
        assertEquals(Hours.of(-15), result);
    }
}
