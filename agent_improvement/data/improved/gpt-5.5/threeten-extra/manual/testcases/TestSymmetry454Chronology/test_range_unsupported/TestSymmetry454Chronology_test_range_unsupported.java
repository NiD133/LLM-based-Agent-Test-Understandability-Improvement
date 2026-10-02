package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_range_unsupported {

    @Test
    public void test_range_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> Symmetry454Date.of(2012, 6, 28).range(MINUTE_OF_DAY));
    }
}
