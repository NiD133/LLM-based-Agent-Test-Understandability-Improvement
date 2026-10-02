package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_getLong_unsupported {

    /**
     * Querying a {@link PaxDate} for a time-based field that a date cannot
     * provide (here, MINUTE_OF_DAY) must fail rather than return a value.
     */
    @Test
    public void test_getLong_rejectsUnsupportedTimeField() {
        PaxDate date = PaxDate.of(2012, 6, 28);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> date.getLong(MINUTE_OF_DAY));
    }
}
