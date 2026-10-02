package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_range_unsupported {

    @Test
    public void test_range_unsupported() {
        // MINUTE_OF_DAY is a time-based field; querying it on a date-only type must throw
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Symmetry010Date.of(2012, 6, 28).range(MINUTE_OF_DAY));
    }
}
