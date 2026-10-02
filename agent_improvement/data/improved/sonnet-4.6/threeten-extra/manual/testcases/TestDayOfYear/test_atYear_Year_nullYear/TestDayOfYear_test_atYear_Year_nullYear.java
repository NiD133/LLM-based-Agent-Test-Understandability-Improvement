package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_Year_nullYear {

    // The day-of-year under test; day 12 is valid in both leap and non-leap years.
    private static final DayOfYear TEST = DayOfYear.of(12);

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

    // Passing a null Year to atYear(Year) must throw NullPointerException.
    @Test
    public void test_atYear_Year_nullYear() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.atYear((Year) null));
    }
}
