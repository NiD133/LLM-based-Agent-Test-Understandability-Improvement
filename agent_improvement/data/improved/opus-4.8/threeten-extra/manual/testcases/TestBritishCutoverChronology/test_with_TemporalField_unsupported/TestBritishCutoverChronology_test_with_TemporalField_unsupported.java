package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_with_TemporalField_unsupported {

    /**
     * MINUTE_OF_DAY is a time-based field that a date cannot represent, so
     * adjusting a BritishCutoverDate with it must be rejected.
     */
    @Test
    public void test_with_TemporalField_unsupported() {
        BritishCutoverDate date = BritishCutoverDate.of(2012, 6, 30);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.with(MINUTE_OF_DAY, 0));
    }
}
