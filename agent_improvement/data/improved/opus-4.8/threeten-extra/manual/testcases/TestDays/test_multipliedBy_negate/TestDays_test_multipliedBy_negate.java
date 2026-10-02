package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#multipliedBy(int)} scales the amount by a negative
 * scalar, producing a correctly signed result.
 */
public class TestDays_test_multipliedBy_negate {

    @Test
    public void multipliedBy_negativeScalar_negatesAndScalesAmount() {
        Days fiveDays = Days.of(5);

        Days result = fiveDays.multipliedBy(-3);

        assertEquals(Days.of(-15), result);
    }
}
