package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void test_until_TemporalUnit_unsupported() {
        JulianDate start = JulianDate.of(2012, 6, 30);
        JulianDate end = JulianDate.of(2012, 7, 1);

        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
