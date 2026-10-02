package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> PaxDate.of(2012, 6, 10).plus(0, MINUTES));
    }
}
