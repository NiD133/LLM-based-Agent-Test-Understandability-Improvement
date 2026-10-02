package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_compareTo {

    private static final int MIN_DAY_OF_MONTH = 1;
    private static final int MAX_DAY_OF_MONTH = 31;

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
        for (int firstDay = MIN_DAY_OF_MONTH; firstDay <= MAX_DAY_OF_MONTH; firstDay++) {
            DayOfMonth first = DayOfMonth.of(firstDay);
            for (int secondDay = MIN_DAY_OF_MONTH; secondDay <= MAX_DAY_OF_MONTH; secondDay++) {
                DayOfMonth second = DayOfMonth.of(secondDay);

                if (firstDay < secondDay) {
                    assertEquals(true, first.compareTo(second) < 0);
                    assertEquals(true, second.compareTo(first) > 0);
                } else if (firstDay > secondDay) {
                    assertEquals(true, first.compareTo(second) > 0);
                    assertEquals(true, second.compareTo(first) < 0);
                } else {
                    assertEquals(0, first.compareTo(second));
                    assertEquals(0, second.compareTo(first));
                }
            }
        }
    }
}
