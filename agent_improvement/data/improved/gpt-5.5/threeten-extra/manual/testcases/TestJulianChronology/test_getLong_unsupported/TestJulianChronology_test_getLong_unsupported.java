package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> JulianDate.of(2012, 6, 30).getLong(MINUTE_OF_DAY));
    }
}
