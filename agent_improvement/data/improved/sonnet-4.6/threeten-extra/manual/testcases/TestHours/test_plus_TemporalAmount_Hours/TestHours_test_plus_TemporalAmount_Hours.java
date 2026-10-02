package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_Hours {

    @Test
    public void test_plus_TemporalAmount_Hours() {
        Hours base = Hours.of(5);

        // Adding zero hours leaves the value unchanged
        assertEquals(Hours.of(5), base.plus(Hours.of(0)));

        // Adding a positive amount increases the hour count
        assertEquals(Hours.of(7), base.plus(Hours.of(2)));

        // Adding a negative amount decreases the hour count
        assertEquals(Hours.of(3), base.plus(Hours.of(-2)));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).plus(Hours.of(1)));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).plus(Hours.of(-1)));
    }
}
