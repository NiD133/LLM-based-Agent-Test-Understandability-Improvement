package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_range_unsupported {

    /**
     * Querying the range of a time-based field such as MINUTE_OF_DAY is not
     * supported on a date-only value, so it must fail fast.
     */
    @Test
    public void test_range_unsupported() {
        Symmetry010Date date = Symmetry010Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
