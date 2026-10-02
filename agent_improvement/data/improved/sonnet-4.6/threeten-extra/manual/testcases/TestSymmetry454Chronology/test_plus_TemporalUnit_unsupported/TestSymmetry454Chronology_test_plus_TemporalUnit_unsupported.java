package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        // MINUTES is not a supported unit for date-only arithmetic in Symmetry454Date
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Symmetry454Date.of(2012, 6, 28).plus(0, MINUTES));
    }
}
