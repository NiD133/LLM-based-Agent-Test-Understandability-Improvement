package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_Hours {

    @Test
    public void test_minus_TemporalAmount_Hours() {
        Hours fiveHours = Hours.of(5);

        // Subtracting zero leaves the value unchanged
        assertEquals(Hours.of(5), fiveHours.minus(Hours.of(0)));

        // Subtracting a positive amount reduces the value
        assertEquals(Hours.of(3), fiveHours.minus(Hours.of(2)));

        // Subtracting a negative amount increases the value
        assertEquals(Hours.of(7), fiveHours.minus(Hours.of(-2)));

        // Subtracting -1 from (MAX_VALUE - 1) yields MAX_VALUE without overflow
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).minus(Hours.of(-1)));

        // Subtracting 1 from (MIN_VALUE + 1) yields MIN_VALUE without overflow
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).minus(Hours.of(1)));
    }
}
