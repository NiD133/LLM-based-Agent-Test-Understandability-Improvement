package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that querying a Symmetry454 date for the value-range of a time-based
 * field is rejected, since the chronology only models calendar dates.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_range_unsupported {

    @Test
    public void range_withTimeBasedField_throwsUnsupportedTemporalType() {
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> date.range(MINUTE_OF_DAY));
    }
}
