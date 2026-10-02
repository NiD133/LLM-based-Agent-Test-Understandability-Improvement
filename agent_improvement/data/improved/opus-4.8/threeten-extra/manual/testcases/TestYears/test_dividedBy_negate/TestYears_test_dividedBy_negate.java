package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#dividedBy(int)} when dividing by a negative divisor.
 */
public class TestYears_test_dividedBy_negate {

    @Test
    public void dividedBy_negativeDivisor_returnsNegatedQuotient() {
        Years twelveYears = Years.of(12);

        // Integer division by a negative divisor: 12 / -3 = -4
        Years result = twelveYears.dividedBy(-3);

        assertEquals(Years.of(-4), result);
    }
}
