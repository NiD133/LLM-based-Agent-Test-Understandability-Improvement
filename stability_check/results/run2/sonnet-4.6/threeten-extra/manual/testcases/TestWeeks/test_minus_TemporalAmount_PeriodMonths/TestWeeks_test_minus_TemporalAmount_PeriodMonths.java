package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    /**
     * Subtracting a month-based Period from Weeks is not supported because months
     * cannot be converted to a whole number of weeks, so a DateTimeException is expected.
     */
    @Test
    public void test_minus_TemporalAmount_PeriodMonths() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Period.ofMonths(2)));
    }
}
