package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focused on {@code atYear(Year)} with a null argument.
 */
public class TestDayOfYear_test_atYear_Year_nullYear {

    /** A fixed sample day-of-year used by the null-argument test. */
    private static final DayOfYear SAMPLE_DAY = DayOfYear.of(12);

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
    // atYear(Year) with a null year
    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_Year_nullYear() {
        // atYear(Year) must reject a null year by throwing NullPointerException.
        assertThrows(NullPointerException.class, () -> SAMPLE_DAY.atYear((Year) null));
    }
}
