package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth#compareTo(DayOfMonth)} together with the two
 * {@code now()} factory methods that the original test exercised.
 */
public class TestDayOfMonth_test_compareTo {

    /** The largest day-of-month value supported by {@link DayOfMonth}. */
    private static final int MAX_DAY_OF_MONTH = 31;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesSystemDefaultZone() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_matchesThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // compareTo(DayOfMonth)
    //-----------------------------------------------------------------------
    @Test
    public void compareTo_ordersByDayValue() {
        // Compare every pair of valid day-of-month values (1..31) in both
        // directions and verify the ordering is consistent with their values.
        for (int firstDay = 1; firstDay <= MAX_DAY_OF_MONTH; firstDay++) {
            DayOfMonth first = DayOfMonth.of(firstDay);
            for (int secondDay = 1; secondDay <= MAX_DAY_OF_MONTH; secondDay++) {
                DayOfMonth second = DayOfMonth.of(secondDay);

                if (firstDay < secondDay) {
                    assertTrue(first.compareTo(second) < 0);
                    assertTrue(second.compareTo(first) > 0);
                } else if (firstDay > secondDay) {
                    assertTrue(first.compareTo(second) > 0);
                    assertTrue(second.compareTo(first) < 0);
                } else {
                    assertEquals(0, first.compareTo(second));
                    assertEquals(0, second.compareTo(first));
                }
            }
        }
    }
}
