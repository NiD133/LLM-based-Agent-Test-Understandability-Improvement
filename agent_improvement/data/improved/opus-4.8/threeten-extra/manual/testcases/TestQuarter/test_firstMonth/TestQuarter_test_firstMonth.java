package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#firstMonth()}.
 * <p>
 * Each quarter spans three calendar months; {@code firstMonth()} returns the
 * first of those three. The expected mapping is:
 * <ul>
 *   <li>Q1 -&gt; January</li>
 *   <li>Q2 -&gt; April</li>
 *   <li>Q3 -&gt; July</li>
 *   <li>Q4 -&gt; October</li>
 * </ul>
 */
public class TestQuarter_test_firstMonth {

    @Test
    public void firstMonth_returnsFirstMonthOfEachQuarter() {
        assertEquals(Month.JANUARY, Quarter.Q1.firstMonth());
        assertEquals(Month.APRIL, Quarter.Q2.firstMonth());
        assertEquals(Month.JULY, Quarter.Q3.firstMonth());
        assertEquals(Month.OCTOBER, Quarter.Q4.firstMonth());
    }
}
