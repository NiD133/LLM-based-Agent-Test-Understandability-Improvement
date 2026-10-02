package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#toString()} renders the amount in ISO-8601 "PnM" form.
 */
public class TestMonths_test_toString {

    @Test
    public void toString_formatsAmountInIso8601() {
        // A positive amount of months.
        assertEquals("P5M", Months.of(5).toString());

        // A negative amount keeps the sign inside the value.
        assertEquals("P-1M", Months.of(-1).toString());
    }
}
