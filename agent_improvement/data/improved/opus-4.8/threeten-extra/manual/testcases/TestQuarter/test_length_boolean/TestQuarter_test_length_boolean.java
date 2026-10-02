package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#length(boolean)}, which reports the number of days in a
 * quarter and takes a flag indicating whether the year is a leap year.
 * <p>
 * Only Q1 differs between common and leap years: it gains a day (90 -&gt; 91)
 * when February has 29 days. Q2 always has 91 days, while Q3 and Q4 always
 * have 92 days, so the leap-year flag never changes their length.
 */
public class TestQuarter_test_length_boolean {

    @Test
    public void test_length_boolean() {
        // Q1 (Jan-Mar): the only quarter affected by the leap-year flag.
        assertEquals(91, Quarter.Q1.length(true),  "Q1 in a leap year");
        assertEquals(90, Quarter.Q1.length(false), "Q1 in a common year");

        // Q2 (Apr-Jun): always 91 days, regardless of the leap-year flag.
        assertEquals(91, Quarter.Q2.length(true),  "Q2 in a leap year");
        assertEquals(91, Quarter.Q2.length(false), "Q2 in a common year");

        // Q3 (Jul-Sep): always 92 days, regardless of the leap-year flag.
        assertEquals(92, Quarter.Q3.length(true),  "Q3 in a leap year");
        assertEquals(92, Quarter.Q3.length(false), "Q3 in a common year");

        // Q4 (Oct-Dec): always 92 days, regardless of the leap-year flag.
        assertEquals(92, Quarter.Q4.length(true),  "Q4 in a leap year");
        assertEquals(92, Quarter.Q4.length(false), "Q4 in a common year");
    }
}
