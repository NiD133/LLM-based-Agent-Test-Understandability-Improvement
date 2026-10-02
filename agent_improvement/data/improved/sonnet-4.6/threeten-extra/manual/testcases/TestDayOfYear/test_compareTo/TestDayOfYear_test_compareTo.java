package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Year;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_compareTo {

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

    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        for (int i = 1; i <= LEAP_YEAR_LENGTH; i++) {
            DayOfYear earlier = DayOfYear.of(i);
            for (int j = 1; j <= LEAP_YEAR_LENGTH; j++) {
                DayOfYear later = DayOfYear.of(j);
                if (i < j) {
                    assertTrue(earlier.compareTo(later) < 0,
                            "Day " + i + " should be less than day " + j);
                    assertTrue(later.compareTo(earlier) > 0,
                            "Day " + j + " should be greater than day " + i);
                } else if (i > j) {
                    assertTrue(earlier.compareTo(later) > 0,
                            "Day " + i + " should be greater than day " + j);
                    assertTrue(later.compareTo(earlier) < 0,
                            "Day " + j + " should be less than day " + i);
                } else {
                    assertEquals(0, earlier.compareTo(later),
                            "Day " + i + " compared to itself should be 0");
                    assertEquals(0, later.compareTo(earlier),
                            "Day " + j + " compared to itself should be 0");
                }
            }
        }
    }
}
