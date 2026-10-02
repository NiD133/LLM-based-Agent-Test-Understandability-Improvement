package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestInternationalFixedChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void test_plus_TemporalUnit_unsupported() {
        InternationalFixedDate date = InternationalFixedDate.of(2012, 6, 28);
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
