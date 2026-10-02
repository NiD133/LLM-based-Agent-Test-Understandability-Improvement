package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#plus(java.time.temporal.TemporalAmount)} where the added
 * amount is itself a {@code Seconds} instance.
 */
public class TestSeconds_test_plus_TemporalAmount_Seconds {

    @Test
    public void test_plus_TemporalAmount_Seconds() {
        Seconds fiveSeconds = Seconds.of(5);

        // Adding zero leaves the value unchanged.
        assertEquals(Seconds.of(5), fiveSeconds.plus(Seconds.of(0)));
        // Adding a positive amount increases the value.
        assertEquals(Seconds.of(7), fiveSeconds.plus(Seconds.of(2)));
        // Adding a negative amount decreases the value.
        assertEquals(Seconds.of(3), fiveSeconds.plus(Seconds.of(-2)));

        // Adding up to the boundaries does not overflow.
        assertEquals(
                Seconds.of(Integer.MAX_VALUE),
                Seconds.of(Integer.MAX_VALUE - 1).plus(Seconds.of(1)));
        assertEquals(
                Seconds.of(Integer.MIN_VALUE),
                Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-1)));
    }
}
