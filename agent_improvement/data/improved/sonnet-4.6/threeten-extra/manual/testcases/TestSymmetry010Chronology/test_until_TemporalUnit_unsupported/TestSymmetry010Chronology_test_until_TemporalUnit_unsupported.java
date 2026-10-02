package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_TemporalUnit_unsupported {

    @Test
    public void test_until_TemporalUnit_unsupported() {
        Symmetry010Date start = Symmetry010Date.of(2012, 6, 28);
        Symmetry010Date end = Symmetry010Date.of(2012, 7, 1);
        // MINUTES is not a date-based unit, so until() must reject it
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
