package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromStartOfYear_leapYear {

    private static final int LEAP_YEAR_LENGTH = 366;

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
    public void test_adjustInto_fromStartOfYear_leapYear() {
        // Verify that adjustInto correctly maps each day-of-year ordinal to the
        // corresponding calendar date when applied to a base date in a leap year (2008).
        LocalDate firstDayOfLeapYear = LocalDate.of(2008, 1, 1);
        LocalDate expectedDate = firstDayOfLeapYear;
        for (int dayOrdinal = 1; dayOrdinal <= LEAP_YEAR_LENGTH; dayOrdinal++) {
            DayOfYear dayOfYear = DayOfYear.of(dayOrdinal);
            assertEquals(
                    expectedDate,
                    dayOfYear.adjustInto(firstDayOfLeapYear),
                    "Day ordinal " + dayOrdinal + " should adjust to " + expectedDate);
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
