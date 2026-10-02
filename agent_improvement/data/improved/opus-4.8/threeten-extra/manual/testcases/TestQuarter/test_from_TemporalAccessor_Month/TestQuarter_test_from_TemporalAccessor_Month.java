package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#from(java.time.temporal.TemporalAccessor)} when the argument is a {@link Month}.
 * <p>
 * The ISO calendar groups the twelve months into four quarters of three months each:
 * Jan-Mar are Q1, Apr-Jun are Q2, Jul-Sep are Q3 and Oct-Dec are Q4.
 */
public class TestQuarter_test_from_TemporalAccessor_Month {

    @Test
    public void test_from_TemporalAccessor_Month() {
        // January to March -> Q1
        assertEquals(Quarter.Q1, Quarter.from(Month.JANUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.FEBRUARY));
        assertEquals(Quarter.Q1, Quarter.from(Month.MARCH));

        // April to June -> Q2
        assertEquals(Quarter.Q2, Quarter.from(Month.APRIL));
        assertEquals(Quarter.Q2, Quarter.from(Month.MAY));
        assertEquals(Quarter.Q2, Quarter.from(Month.JUNE));

        // July to September -> Q3
        assertEquals(Quarter.Q3, Quarter.from(Month.JULY));
        assertEquals(Quarter.Q3, Quarter.from(Month.AUGUST));
        assertEquals(Quarter.Q3, Quarter.from(Month.SEPTEMBER));

        // October to December -> Q4
        assertEquals(Quarter.Q4, Quarter.from(Month.OCTOBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.NOVEMBER));
        assertEquals(Quarter.Q4, Quarter.from(Month.DECEMBER));
    }
}
