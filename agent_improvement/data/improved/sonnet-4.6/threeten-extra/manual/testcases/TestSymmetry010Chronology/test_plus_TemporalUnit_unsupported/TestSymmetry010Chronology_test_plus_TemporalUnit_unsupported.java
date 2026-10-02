package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_TemporalUnit_unsupported {

    /**
     * Verifies that adding an unsupported time-based unit (MINUTES) to a Symmetry010Date
     * throws UnsupportedTemporalTypeException. Symmetry010Date only supports date-based
     * units (days, weeks, months, years, etc.) — sub-day units are not meaningful for a date.
     */
    @Test
    public void test_plus_TemporalUnit_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Symmetry010Date.of(2012, 6, 28).plus(0, MINUTES));
    }
}
