package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        // MINUTES is a time-based unit; JulianDate only supports date-based units
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> JulianDate.of(2012, 6, 30).plus(0, MINUTES));
    }
}
