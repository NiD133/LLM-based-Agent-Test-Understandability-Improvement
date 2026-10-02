package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_from_wrongUnit_noConversion {

    @Test
    public void test_from_wrongUnit_noConversion() {
        // Period.ofDays(2) contains only DAYS, which cannot be converted to whole months
        assertThrows(DateTimeException.class, () -> Months.from(Period.ofDays(2)));
    }
}
