package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void test_until_TemporalUnit_unsupported() {
        PaxDate start = PaxDate.of(2012, 6, 28);
        PaxDate end = PaxDate.of(2012, 7, 1);

        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
