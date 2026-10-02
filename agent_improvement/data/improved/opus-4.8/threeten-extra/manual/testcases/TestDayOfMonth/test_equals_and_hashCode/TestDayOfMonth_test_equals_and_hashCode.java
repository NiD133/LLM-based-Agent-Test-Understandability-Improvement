package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import com.google.common.testing.EqualsTester;

/**
 * Tests {@link DayOfMonth} equality/hash-code semantics, plus the {@code now()}
 * factory methods that feed into instances being compared.
 */
public class TestDayOfMonth_test_equals_and_hashCode {

    /** The largest day-of-month value supported by {@link DayOfMonth} (the 31st). */
    private static final int LAST_DAY_OF_MONTH = 31;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() must report the same day as LocalDate.now() in the default zone.
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfMonth.now(zone) must report the same day as LocalDate.now(zone) for the same zone.
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // equals() and hashCode()
    //-----------------------------------------------------------------------
    @Test
    public void test_equals_and_hashCode() {
        // Each supported day (1..31) forms its own equality group: two instances of
        // the same day must be equal (and share a hash code), while instances of
        // different days must not. EqualsTester also checks the equals() contract
        // against null and unrelated types.
        EqualsTester equalsTester = new EqualsTester();
        for (int day = 1; day <= LAST_DAY_OF_MONTH; day++) {
            equalsTester.addEqualityGroup(DayOfMonth.of(day), DayOfMonth.of(day));
        }
        equalsTester.testEquals();
    }
}
