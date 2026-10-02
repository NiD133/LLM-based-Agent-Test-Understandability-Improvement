package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#multipliedBy(int)} scales the amount by a
 * negative scalar, which both multiplies the magnitude and flips the sign.
 */
public class TestMonths_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_scalesAndNegatesAmount() {
        Months fiveMonths = Months.of(5);

        Months result = fiveMonths.multipliedBy(-3);

        assertEquals(Months.of(-15), result);
    }
}
