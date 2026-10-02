package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear {

    private static final int STANDARD_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear() {
        LocalDate base = LocalDate.of(2007, 12, 31);
        LocalDate expected = LocalDate.of(2007, 1, 1);

        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);
            assertEquals(expected, test.adjustInto(base));
            expected = expected.plusDays(1);
        }
    }
}
