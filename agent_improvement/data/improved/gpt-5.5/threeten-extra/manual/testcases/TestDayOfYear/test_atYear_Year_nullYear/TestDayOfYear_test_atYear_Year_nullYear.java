package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junitpioneer.jupiter.RetryingTest;
import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_atYear_Year_nullYear {

    private static final DayOfYear DAY_OF_YEAR = DayOfYear.of(12);

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

    @Test
    public void test_atYear_Year_nullYear() {
        Year nullYear = null;
        assertThrows(NullPointerException.class, () -> DAY_OF_YEAR.atYear(nullYear));
    }
}
