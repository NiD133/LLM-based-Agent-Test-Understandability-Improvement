package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        // MINUTES is not a supported unit for date-only arithmetic; plus() must throw
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> BritishCutoverDate.of(2012, 6, 30).plus(0, MINUTES));
    }
}
