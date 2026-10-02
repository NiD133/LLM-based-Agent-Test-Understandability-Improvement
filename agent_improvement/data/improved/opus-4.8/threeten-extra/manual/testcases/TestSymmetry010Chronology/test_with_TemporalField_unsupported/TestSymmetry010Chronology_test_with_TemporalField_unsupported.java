package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry010Date} rejects time-based temporal fields.
 *
 * <p>Symmetry010Date is a date-only type, so a time-of-day field such as
 * {@code MINUTE_OF_DAY} is not supported. Calling {@code with} using such a
 * field must fail with {@link UnsupportedTemporalTypeException}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        Symmetry010Date date = Symmetry010Date.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class,
                () -> date.with(MINUTE_OF_DAY, 10));
    }
}
