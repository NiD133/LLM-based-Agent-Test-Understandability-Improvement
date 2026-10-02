package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_Minutes {

    @Test
    public void test_plus_TemporalAmount_Minutes() {
        Minutes fiveMinutes = Minutes.of(5);

        // Adding zero leaves the value unchanged
        assertEquals(Minutes.of(5), fiveMinutes.plus(Minutes.of(0)));

        // Adding a positive amount increases the value
        assertEquals(Minutes.of(7), fiveMinutes.plus(Minutes.of(2)));

        // Adding a negative amount decreases the value
        assertEquals(Minutes.of(3), fiveMinutes.plus(Minutes.of(-2)));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }
}
