package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#multipliedBy(int)} produces a negative result
 * when a positive amount of hours is multiplied by a negative scalar.
 */
public class TestHours_test_multipliedBy_negate {

    @Test
    public void multiplyingByNegativeScalarNegatesAndScalesTheAmount() {
        Hours fiveHours = Hours.of(5);

        Hours result = fiveHours.multipliedBy(-3);

        assertEquals(Hours.of(-15), result);
    }
}
