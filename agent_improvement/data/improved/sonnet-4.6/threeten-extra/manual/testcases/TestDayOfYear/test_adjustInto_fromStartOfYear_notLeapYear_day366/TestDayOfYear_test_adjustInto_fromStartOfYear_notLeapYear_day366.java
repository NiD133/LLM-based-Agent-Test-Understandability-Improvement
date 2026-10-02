package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromStartOfYear_notLeapYear_day366 {

    // Day 366 only exists in leap years; applying it to a non-leap year must fail.
    private static final int DAY_366 = 366;

    @Test
    public void test_adjustInto_fromStartOfYear_notLeapYear_day366() {
        LocalDate base = LocalDate.of(2007, 1, 1); // 2007 is not a leap year
        DayOfYear day366 = DayOfYear.of(DAY_366);
        assertThrows(DateTimeException.class, () -> day366.adjustInto(base));
    }
}
