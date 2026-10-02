package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_leapYear {

    private static final int LEAP_YEAR_LENGTH = 366;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_adjustInto_fromEndOfYear_leapYear() {
        LocalDate endOfLeapYear = LocalDate.of(2008, 12, 31);
        LocalDate expectedAdjustedDate = LocalDate.of(2008, 1, 1);

        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);
            assertEquals(expectedAdjustedDate, test.adjustInto(endOfLeapYear));
            expectedAdjustedDate = expectedAdjustedDate.plusDays(1);
        }
    }
}
