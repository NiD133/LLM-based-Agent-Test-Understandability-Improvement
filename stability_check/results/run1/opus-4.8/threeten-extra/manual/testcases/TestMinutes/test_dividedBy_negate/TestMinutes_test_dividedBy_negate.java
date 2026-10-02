package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Minutes#dividedBy(int)} when dividing by a negative divisor.
 */
public class TestMinutes_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        // Dividing 12 minutes by -3 should yield -4 minutes (integer division).
        Minutes twelveMinutes = Minutes.of(12);
        Minutes result = twelveMinutes.dividedBy(-3);

        assertEquals(Minutes.of(-4), result);
    }
}
