package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_from_TemporalAccessor_Month {

    @Test
    public void test_from_TemporalAccessor_Month() {
        assertEquals(Quarter.Q1, Quarter.from(Month.JANUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.FEBRUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.MARCH));

        assertEquals(Quarter.Q2, Quarter.from(Month.APRIL));
        assertEquals(Quarter.Q2, Quarter.from(Month.MAY));
        assertEquals(Quarter.Q2, Quarter.from(Month.JUNE));

        assertEquals(Quarter.Q3, Quarter.from(Month.JULY));
        assertEquals(Quarter.Q3, Quarter.from(Month.AUGUST));
        assertEquals(Quarter.Q3, Quarter.from(Month.SEPTEMBER));

        assertEquals(Quarter.Q4, Quarter.from(Month.OCTOBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.NOVEMBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.DECEMBER));
    }
}
