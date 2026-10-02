package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        // MINUTE_OF_DAY is a time-based field; Symmetry454Date is date-only and must reject it
        Symmetry454Date date = Symmetry454Date.of(2012, 6, 28);
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.getLong(MINUTE_OF_DAY));
    }
}
