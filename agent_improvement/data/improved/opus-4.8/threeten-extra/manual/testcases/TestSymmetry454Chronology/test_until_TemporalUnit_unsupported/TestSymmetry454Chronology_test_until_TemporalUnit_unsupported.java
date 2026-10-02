package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Date#until} rejects a time-based unit.
 *
 * <p>A {@code Symmetry454Date} is a date without a time component, so measuring
 * the distance between two dates in {@link java.time.temporal.ChronoUnit#MINUTES}
 * is not meaningful and must fail.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_TemporalUnit_unsupported {

    @Test
    public void until_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        Symmetry454Date start = Symmetry454Date.of(2012, 6, 28);
        Symmetry454Date end = Symmetry454Date.of(2012, 7, 1);

        // MINUTES is a time-based unit, which a date-only type cannot measure.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
