package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#of(int)} returns the singleton whose
 * {@link Quarter#getValue()} matches the requested quarter number.
 */
public class TestQuarter_test_of_int_singleton {

    @Test
    public void of_returnsQuarterWithMatchingValue() {
        // The four valid quarter numbers are 1 (Q1) through 4 (Q4).
        for (int quarterNumber = 1; quarterNumber <= 4; quarterNumber++) {
            Quarter quarter = Quarter.of(quarterNumber);

            assertEquals(quarterNumber, quarter.getValue());
        }
    }
}
