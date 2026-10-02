package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_Minutes {

    // Base value used across all assertions in this test
    private static final Minutes FIVE_MINUTES = Minutes.of(5);

    @Test
    public void test_plus_TemporalAmount_Minutes() {
        // Adding zero minutes leaves the value unchanged
        assertEquals(Minutes.of(5), FIVE_MINUTES.plus(Minutes.of(0)));

        // Adding a positive amount increases the total
        assertEquals(Minutes.of(7), FIVE_MINUTES.plus(Minutes.of(2)));

        // Adding a negative amount decreases the total
        assertEquals(Minutes.of(3), FIVE_MINUTES.plus(Minutes.of(-2)));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(
                Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(
                Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }
}
