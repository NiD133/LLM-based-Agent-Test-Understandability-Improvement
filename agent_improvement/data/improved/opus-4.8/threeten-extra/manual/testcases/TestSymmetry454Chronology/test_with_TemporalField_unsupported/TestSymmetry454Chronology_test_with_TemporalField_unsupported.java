package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link Symmetry454Date} with a time-based field is rejected.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        // A Symmetry454 date carries no time-of-day information, so adjusting it with a
        // time-based field such as MINUTE_OF_DAY must be rejected.
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.with(MINUTE_OF_DAY, 10));
    }
}
