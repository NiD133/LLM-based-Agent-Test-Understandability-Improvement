package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear#compareTo(DayOfYear)} (plus the closely related
 * {@code now} factory methods that share this fixture).
 */
public class TestDayOfYear_test_compareTo {

    /** A leap year has 366 days, which is the widest range {@link DayOfYear} supports. */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Compares every pair of day-of-year values in 1..366 and checks that
     * {@code compareTo} is sign-correct and antisymmetric:
     * <ul>
     *   <li>a smaller day compares negative against a larger day (and vice versa),</li>
     *   <li>equal days compare as zero in either direction.</li>
     * </ul>
     */
    @Test
    public void test_compareTo() {
        for (int dayA = 1; dayA <= LEAP_YEAR_LENGTH; dayA++) {
            DayOfYear a = DayOfYear.of(dayA);
            for (int dayB = 1; dayB <= LEAP_YEAR_LENGTH; dayB++) {
                DayOfYear b = DayOfYear.of(dayB);
                if (dayA < dayB) {
                    assertTrue(a.compareTo(b) < 0);
                    assertTrue(b.compareTo(a) > 0);
                } else if (dayA > dayB) {
                    assertTrue(a.compareTo(b) > 0);
                    assertTrue(b.compareTo(a) < 0);
                } else {
                    assertEquals(0, a.compareTo(b));
                    assertEquals(0, b.compareTo(a));
                }
            }
        }
    }
}
