package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_PeriodDays {

    @Test
    public void test_minus_TemporalAmount_PeriodDays() {
        // Period.ofDays(2) contains a DAYS unit that cannot be converted to months,
        // so minus() must reject it with DateTimeException.
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Period.ofDays(2)));
    }
}
