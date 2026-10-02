package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear_day366 {

    private static final int LEAP_YEAR_LENGTH = 366;

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear_day366() {
        LocalDate base = LocalDate.of(2007, 12, 31);
        DayOfYear test = DayOfYear.of(LEAP_YEAR_LENGTH);

        assertThrows(DateTimeException.class, () -> test.adjustInto(base));
    }
}
