package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_multipliedBy_negate {

    @Test
    public void test_multipliedBy_negate() {
        // Multiplying a positive amount by a negative scalar should negate the result
        Days fiveDays = Days.of(5);
        Days result = fiveDays.multipliedBy(-3);
        assertEquals(Days.of(-15), result);
    }
}
