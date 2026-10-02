package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_from_wrongUnit_noConversion {

    // Days.from() must reject a Period whose units (months) cannot be converted to days.
    @Test
    public void test_from_wrongUnit_noConversion() {
        assertThrows(DateTimeException.class, () -> Days.from(Period.ofMonths(2)));
    }
}
