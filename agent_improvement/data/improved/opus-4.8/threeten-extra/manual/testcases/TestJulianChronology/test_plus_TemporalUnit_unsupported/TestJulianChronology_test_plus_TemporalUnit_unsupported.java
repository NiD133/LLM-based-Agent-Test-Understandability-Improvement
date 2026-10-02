package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding a time-based unit to a {@link JulianDate} is rejected.
 * <p>
 * A {@code JulianDate} represents a date only, so it cannot support
 * time-based units such as {@link java.time.temporal.ChronoUnit#MINUTES MINUTES}.
 * Attempting to add such a unit must throw {@link UnsupportedTemporalTypeException}.
 */
public class TestJulianChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalTypeException() {
        JulianDate date = JulianDate.of(2012, 6, 30);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
