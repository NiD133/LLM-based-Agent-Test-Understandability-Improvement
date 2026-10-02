package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_range_unsupported {

    /**
     * MINUTE_OF_DAY is a time-based field, so a date-only BritishCutoverDate
     * cannot provide a value range for it and must reject the query.
     */
    @Test
    public void range_rejectsTimeBasedField() {
        BritishCutoverDate date = BritishCutoverDate.of(2012, 6, 30);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
