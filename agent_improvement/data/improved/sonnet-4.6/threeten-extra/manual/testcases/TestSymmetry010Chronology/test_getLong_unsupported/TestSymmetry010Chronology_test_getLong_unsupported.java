package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestSymmetry010Chronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        // MINUTE_OF_DAY is a time-based field; Symmetry010Date is date-only, so it must reject it
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Symmetry010Date.of(2012, 6, 28).getLong(MINUTE_OF_DAY));
    }
}
