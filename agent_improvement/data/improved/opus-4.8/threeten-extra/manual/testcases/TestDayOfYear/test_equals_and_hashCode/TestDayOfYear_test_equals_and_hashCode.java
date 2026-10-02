package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import com.google.common.testing.EqualsTester;

/**
 * Tests {@link DayOfYear} equality, hashing, and the "now" factory methods.
 */
public class TestDayOfYear_test_equals_and_hashCode {

    /** The longest possible year (a leap year), giving the full range of valid day-of-year values 1..366. */
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        // Two DayOfYear instances are equal if and only if they hold the same value.
        // Build one equality group per valid day (1..366); instances within a group
        // must be equal to each other and unequal to instances in any other group.
        EqualsTester equalsTester = new EqualsTester();
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            equalsTester.addEqualityGroup(DayOfYear.of(dayOfYear), DayOfYear.of(dayOfYear));
        }
        equalsTester.testEquals();
    }
}
