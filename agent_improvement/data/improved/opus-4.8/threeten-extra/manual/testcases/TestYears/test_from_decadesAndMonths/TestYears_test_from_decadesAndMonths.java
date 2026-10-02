package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#from(java.time.temporal.TemporalAmount)} when the source
 * amount carries multiple temporal units that must be combined into whole years.
 */
public class TestYears_test_from_decadesAndMonths {

    @Test
    public void from_combinesDecadesAndMonthsIntoWholeYears() {
        // MockDecadesMonths(2, -12) represents 2 decades and -12 months.
        // 2 decades = 20 years; -12 months = -1 year; total = 19 years.
        Years result = Years.from(new MockDecadesMonths(2, -12));

        assertEquals(Years.of(19), result);
    }
}
