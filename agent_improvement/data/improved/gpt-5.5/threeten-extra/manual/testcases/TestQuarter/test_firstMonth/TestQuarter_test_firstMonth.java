package org.threeten.extra;

import static org.junit.Assert.assertEquals;

import java.time.Month;

import org.junit.Test;

public class TestQuarter_test_firstMonth {

    @Test
    public void test_firstMonth() {
        assertFirstMonth(Quarter.Q1, Month.JANUARY);
        assertFirstMonth(Quarter.Q2, Month.APRIL);
        assertFirstMonth(Quarter.Q3, Month.JULY);
        assertFirstMonth(Quarter.Q4, Month.OCTOBER);
    }

    private static void assertFirstMonth(Quarter quarter, Month expectedFirstMonth) {
        assertEquals(expectedFirstMonth, quarter.firstMonth());
    }
}
