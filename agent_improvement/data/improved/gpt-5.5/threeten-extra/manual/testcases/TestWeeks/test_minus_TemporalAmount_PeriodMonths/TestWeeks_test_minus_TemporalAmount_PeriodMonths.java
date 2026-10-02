package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    @Test
    public void test_minus_TemporalAmount_PeriodMonths() {
        // Months cannot be converted to a whole number of weeks.
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Period.ofMonths(2)));
    }
}
