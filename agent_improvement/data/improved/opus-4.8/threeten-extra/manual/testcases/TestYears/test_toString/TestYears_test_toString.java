package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#toString()} renders the amount in the ISO-8601
 * {@code PnY} format, including the sign for negative amounts.
 */
public class TestYears_test_toString {

    @Test
    public void toString_formatsPositiveYearsAsIso8601() {
        Years fiveYears = Years.of(5);

        assertEquals("P5Y", fiveYears.toString());
    }

    @Test
    public void toString_formatsNegativeYearsWithSign() {
        Years minusOneYear = Years.of(-1);

        assertEquals("P-1Y", minusOneYear.toString());
    }
}
