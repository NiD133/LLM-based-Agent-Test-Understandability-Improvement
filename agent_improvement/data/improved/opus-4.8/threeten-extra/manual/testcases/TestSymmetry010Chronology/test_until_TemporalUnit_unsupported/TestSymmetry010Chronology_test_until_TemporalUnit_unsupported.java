package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_TemporalUnit_unsupported {

    /**
     * {@code until} computes an amount between two dates measured in a given unit.
     * A time-based unit such as {@code MINUTES} cannot be derived from two dates,
     * so it must be rejected with an {@link UnsupportedTemporalTypeException}.
     */
    @Test
    public void test_until_TemporalUnit_unsupported() {
        Symmetry010Date start = Symmetry010Date.of(2012, 6, 28);
        Symmetry010Date end = Symmetry010Date.of(2012, 7, 1);

        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
