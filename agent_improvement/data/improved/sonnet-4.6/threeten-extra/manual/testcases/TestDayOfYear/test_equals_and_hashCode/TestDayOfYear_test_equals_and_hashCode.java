package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import com.google.common.testing.EqualsTester;

public class TestDayOfYear_test_equals_and_hashCode {

    // A leap year has 366 days — the maximum valid day-of-year value
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

    // Verifies that two DayOfYear instances with the same day value are equal and share the
    // same hashCode, while instances with different day values are not equal to each other.
    @Test
    public void test_equals_and_hashCode() {
        EqualsTester equalsTester = new EqualsTester();
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            equalsTester.addEqualityGroup(DayOfYear.of(i), DayOfYear.of(i));
        }
        equalsTester.testEquals();
    }
}
