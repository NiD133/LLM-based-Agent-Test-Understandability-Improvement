package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BritishCutoverDate#plus(long, java.time.temporal.TemporalUnit)}
 * rejects time-based units, which a date has no way to represent.
 */
public class TestBritishCutoverChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        BritishCutoverDate date = BritishCutoverDate.of(2012, 6, 30);

        // MINUTES is a time unit; adding it to a date is unsupported.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
