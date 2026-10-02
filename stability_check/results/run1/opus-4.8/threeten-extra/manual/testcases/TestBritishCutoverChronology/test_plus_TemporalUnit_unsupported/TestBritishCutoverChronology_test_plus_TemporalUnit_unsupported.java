package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_plus_TemporalUnit_unsupported {

    /**
     * Adding an amount expressed in a time-based unit (minutes) to a date-only
     * {@link BritishCutoverDate} is not supported and must fail fast.
     */
    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        BritishCutoverDate date = BritishCutoverDate.of(2012, 6, 30);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
