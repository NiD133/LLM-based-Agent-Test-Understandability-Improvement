package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_minus_Period_ISO {

    // JulianDate.minus() rejects ISO Period because ISO chronology differs from Julian;
    // mixing calendars is not allowed and must throw DateTimeException.
    @Test
    public void test_minus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> JulianDate.of(2014, 5, 26).minus(Period.ofMonths(2)));
    }
}
