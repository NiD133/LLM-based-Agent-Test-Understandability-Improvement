package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_TemporalAccessor_notLeapYear {

    private static final int NON_LEAP_YEAR_LENGTH = 365;

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

    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        LocalDate currentDate = LocalDate.of(2007, 1, 1);

        for (int expectedDayOfYear = 1; expectedDayOfYear <= NON_LEAP_YEAR_LENGTH; expectedDayOfYear++) {
            DayOfYear actualDayOfYear = DayOfYear.from(currentDate);

            assertEquals(expectedDayOfYear, actualDayOfYear.getValue());
            currentDate = currentDate.plusDays(1);
        }

        DayOfYear firstDayOfFollowingYear = DayOfYear.from(currentDate);
        assertEquals(1, firstDayOfFollowingYear.getValue());
    }
}
