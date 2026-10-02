package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the state of a {@code Seconds} instance created from a single
 * positive second via {@link Seconds#of(int)}.
 */
public class TestSeconds_test_ofPlusOne {

    @Test
    public void of_one_second_isPositiveAndHasAmountOne() {
        Seconds oneSecond = Seconds.of(1);

        // The stored amount should match the value passed to of(1).
        assertEquals(1, oneSecond.getAmount());

        // One second is a positive, non-zero amount.
        assertFalse(oneSecond.isNegative());
        assertFalse(oneSecond.isZero());
        assertTrue(oneSecond.isPositive());
    }
}
