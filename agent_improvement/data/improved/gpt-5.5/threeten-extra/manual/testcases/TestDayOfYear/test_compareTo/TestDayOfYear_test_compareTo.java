package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_compareTo {

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
    public void test_compareTo() {
        for (int leftDay = 1; leftDay <= LEAP_YEAR_LENGTH; leftDay++) {
            DayOfYear left = DayOfYear.of(leftDay);
            for (int rightDay = 1; rightDay <= LEAP_YEAR_LENGTH; rightDay++) {
                DayOfYear right = DayOfYear.of(rightDay);

                assertComparisonOrder(leftDay, left, rightDay, right);
            }
        }
    }

    private void assertComparisonOrder(int leftDay, DayOfYear left, int rightDay, DayOfYear right) {
        if (leftDay < rightDay) {
            assertEquals(true, left.compareTo(right) < 0);
            assertEquals(true, right.compareTo(left) > 0);
        } else if (leftDay > rightDay) {
            assertEquals(true, left.compareTo(right) > 0);
            assertEquals(true, right.compareTo(left) < 0);
        } else {
            assertEquals(0, left.compareTo(right));
            assertEquals(0, right.compareTo(left));
        }
    }
}
