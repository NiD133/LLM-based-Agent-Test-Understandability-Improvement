package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#toString()} renders the amount in ISO-8601 'PnD' format.
 */
public class TestDays_test_toString {

    @Test
    public void toString_formatsAmountInIso8601() {
        // A positive amount is rendered as "P<n>D".
        assertEquals("P5D", Days.of(5).toString());

        // A negative amount keeps the sign immediately before the number.
        assertEquals("P-1D", Days.of(-1).toString());
    }
}
