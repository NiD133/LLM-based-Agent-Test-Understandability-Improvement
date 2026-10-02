package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_getLong_unsupported {

    /**
     * MINUTE_OF_DAY is a time-of-day field, so a date-only Symmetry454Date
     * has no value for it and getLong must reject the request.
     */
    @Test
    public void test_getLong_unsupported() {
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.getLong(MINUTE_OF_DAY));
    }
}
