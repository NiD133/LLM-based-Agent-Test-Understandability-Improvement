package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_Period_ISO {

    // PaxDate.plus(Period) must reject ISO-based Period instances because
    // Period is tied to the ISO calendar and cannot be applied to a Pax date.
    @Test
    public void test_plus_Period_ISO() {
        assertThrows(DateTimeException.class, () -> PaxDate.of(2014, 5, 26).plus(Period.ofMonths(2)));
    }
}
