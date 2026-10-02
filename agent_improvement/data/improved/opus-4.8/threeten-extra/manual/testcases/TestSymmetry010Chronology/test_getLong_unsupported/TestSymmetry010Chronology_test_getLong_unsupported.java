package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry010Date#getLong} rejects a field it does not support.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        // MINUTE_OF_DAY is a time-of-day field and has no meaning for a date-only value.
        Symmetry010Date date = Symmetry010Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.getLong(MINUTE_OF_DAY));
    }
}
