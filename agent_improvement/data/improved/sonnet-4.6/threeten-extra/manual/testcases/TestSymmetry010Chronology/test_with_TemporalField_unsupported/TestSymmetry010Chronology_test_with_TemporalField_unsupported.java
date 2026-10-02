package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        // MINUTE_OF_DAY is a time-based field; Symmetry010Date is date-only and must reject it
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Symmetry010Date.of(2012, 6, 28).with(MINUTE_OF_DAY, 10));
    }
}
