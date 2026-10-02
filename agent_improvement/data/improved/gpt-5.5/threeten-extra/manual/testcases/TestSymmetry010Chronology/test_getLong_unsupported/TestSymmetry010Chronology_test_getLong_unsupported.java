package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

class TestSymmetry010Chronology_test_getLong_unsupported {

    @Test
    void test_getLong_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> Symmetry010Date.of(2012, 6, 28).getLong(MINUTE_OF_DAY));
    }
}
