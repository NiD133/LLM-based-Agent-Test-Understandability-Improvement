package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Months#dividedBy(int)} when dividing by a negative divisor.
 */
public class TestMonths_test_dividedBy_negate {

    @Test
    public void dividingByNegativeDivisor_negatesAndDividesAmount() {
        Months twelveMonths = Months.of(12);

        // 12 months divided by -3 yields -4 months.
        Months result = twelveMonths.dividedBy(-3);

        assertEquals(Months.of(-4), result);
    }
}
