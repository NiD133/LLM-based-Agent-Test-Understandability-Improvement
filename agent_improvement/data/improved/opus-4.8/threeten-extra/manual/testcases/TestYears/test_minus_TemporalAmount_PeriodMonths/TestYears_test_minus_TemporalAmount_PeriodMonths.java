package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract cannot be expressed as a whole number of years.
 */
public class TestYears_test_minus_TemporalAmount_PeriodMonths {

    /**
     * Subtracting a period of months that is not a whole number of years must
     * fail: 2 months cannot be converted to years, so a DateTimeException is thrown.
     */
    @Test
    public void minus_periodOfTwoMonths_throwsBecauseNotWholeYears() {
        Years oneYear = Years.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneYear.minus(twoMonths));
    }
}
