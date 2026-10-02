package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromStartOfYear_notLeapYear {

    private static final int STANDARD_YEAR_LENGTH = 365;

    @Test
    public void test_adjustInto_fromStartOfYear_notLeapYear() {
        LocalDate base = LocalDate.of(2007, 1, 1);
        LocalDate expected = base;

        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(expected, test.adjustInto(base));
            expected = expected.plusDays(1);
        }
    }
}
