package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_adjustInto_fromEndOfYear_leapYear {

    private static final int LEAP_YEAR = 2008;
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
        LocalDate base = LocalDate.of(LEAP_YEAR, 12, 31);
        LocalDate expected = LocalDate.of(LEAP_YEAR, 1, 1);
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            DayOfYear test = DayOfYear.of(i);
            assertEquals(expected, test.adjustInto(base));
            expected = expected.plusDays(1);
        }
    }
}
