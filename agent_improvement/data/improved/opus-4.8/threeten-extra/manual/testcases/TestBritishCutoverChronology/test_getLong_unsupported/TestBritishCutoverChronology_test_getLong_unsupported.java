package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverDate#getLong(java.time.temporal.TemporalField)}
 * rejects time-based fields, which a date has no value for.
 */
public class TestBritishCutoverChronology_test_getLong_unsupported {

    @Test
    public void getLong_withTimeBasedField_throwsUnsupported() {
        BritishCutoverDate date = BritishCutoverDate.of(2012, 6, 30);

        // MINUTE_OF_DAY is a time field; a date cannot supply it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.getLong(MINUTE_OF_DAY));
    }
}
