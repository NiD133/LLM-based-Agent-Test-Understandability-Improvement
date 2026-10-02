package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear {

    // A standard (non-leap) year has exactly 365 days
    private static final int STANDARD_YEAR_LENGTH = 365;

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear() {
        LocalDate base = LocalDate.of(2007, 12, 31);
        LocalDate expected = LocalDate.of(2007, 1, 1);
        for (int i = 1; i <= STANDARD_YEAR_LENGTH; i++) {
            DayOfYear test = DayOfYear.of(i);
            assertEquals(expected, test.adjustInto(base));
            expected = expected.plusDays(1);
        }
    }
}
