package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_compareTo {

    private static final int MAX_LENGTH = 31;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo() {
        for (int i = 1; i <= MAX_LENGTH; i++) {
            DayOfMonth a = DayOfMonth.of(i);
            for (int j = 1; j <= MAX_LENGTH; j++) {
                DayOfMonth b = DayOfMonth.of(j);
                if (i < j) {
                    assertTrue(a.compareTo(b) < 0,
                            "day " + i + " should be less than day " + j);
                    assertTrue(b.compareTo(a) > 0,
                            "day " + j + " should be greater than day " + i);
                } else if (i > j) {
                    assertTrue(a.compareTo(b) > 0,
                            "day " + i + " should be greater than day " + j);
                    assertTrue(b.compareTo(a) < 0,
                            "day " + j + " should be less than day " + i);
                } else {
                    assertEquals(0, a.compareTo(b),
                            "day " + i + " compared to itself should return 0");
                    assertEquals(0, b.compareTo(a),
                            "day " + j + " compared to itself should return 0");
                }
            }
        }
    }
}
