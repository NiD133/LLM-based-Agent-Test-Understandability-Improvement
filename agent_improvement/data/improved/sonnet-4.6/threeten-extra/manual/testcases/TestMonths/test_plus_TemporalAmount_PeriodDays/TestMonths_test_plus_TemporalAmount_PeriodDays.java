package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_PeriodDays {

    // Days cannot be converted to a whole number of months, so plus() must reject them.
    @Test
    public void test_plus_TemporalAmount_PeriodDays() {
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Period.ofDays(2)));
    }
}
