package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_PeriodMonths {

    // Weeks only supports week-based amounts; adding a month-based Period
    // cannot be converted to a whole number of weeks and must throw.
    @Test
    public void test_plus_TemporalAmount_PeriodMonths() {
        Weeks oneWeek = Weeks.of(1);
        Period twoMonths = Period.ofMonths(2);
        assertThrows(DateTimeException.class, () -> oneWeek.plus(twoMonths));
    }
}
