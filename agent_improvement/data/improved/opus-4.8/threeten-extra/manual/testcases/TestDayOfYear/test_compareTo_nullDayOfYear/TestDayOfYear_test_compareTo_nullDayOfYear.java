package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_compareTo_nullDayOfYear {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // compareTo(DayOfYear)
    //-----------------------------------------------------------------------
    @Test
    public void test_compareTo_nullDayOfYear() {
        DayOfYear test = DayOfYear.of(1);
        DayOfYear nullDayOfYear = null;
        // Comparing against a null day-of-year must throw NullPointerException.
        assertThrows(NullPointerException.class, () -> test.compareTo(nullDayOfYear));
    }
}
