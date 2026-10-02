package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_isValidYearMonth_30 {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_isValidYearMonth_30() {
        DayOfMonth test = DayOfMonth.of(30);
        int[][] monthValidity = {
                {1, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {5, 1},
                {6, 1},
                {7, 1},
                {8, 1},
                {9, 1},
                {10, 1},
                {11, 1},
                {12, 1},
        };

        for (int[] monthAndExpectedValidity : monthValidity) {
            int month = monthAndExpectedValidity[0];
            boolean expectedValidity = monthAndExpectedValidity[1] == 1;

            assertEquals(expectedValidity, test.isValidYearMonth(YearMonth.of(2012, month)));
        }
    }
}
