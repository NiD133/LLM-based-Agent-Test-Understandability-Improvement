package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#ofMonth(int)} maps each month-of-year (1-12) to the
 * correct {@code Quarter} singleton:
 * <ul>
 *   <li>January, February, March (1-3)   &rarr; Q1</li>
 *   <li>April, May, June (4-6)           &rarr; Q2</li>
 *   <li>July, August, September (7-9)    &rarr; Q3</li>
 *   <li>October, November, December (10-12) &rarr; Q4</li>
 * </ul>
 */
public class TestQuarter_test_ofMonth_int_singleton {

    @Test
    public void test_ofMonth_int_singleton() {
        // First quarter: January to March
        assertSame(Quarter.Q1, Quarter.ofMonth(1));
        assertSame(Quarter.Q1, Quarter.ofMonth(2));
        assertSame(Quarter.Q1, Quarter.ofMonth(3));

        // Second quarter: April to June
        assertSame(Quarter.Q2, Quarter.ofMonth(4));
        assertSame(Quarter.Q2, Quarter.ofMonth(5));
        assertSame(Quarter.Q2, Quarter.ofMonth(6));

        // Third quarter: July to September
        assertSame(Quarter.Q3, Quarter.ofMonth(7));
        assertSame(Quarter.Q3, Quarter.ofMonth(8));
        assertSame(Quarter.Q3, Quarter.ofMonth(9));

        // Fourth quarter: October to December
        assertSame(Quarter.Q4, Quarter.ofMonth(10));
        assertSame(Quarter.Q4, Quarter.ofMonth(11));
        assertSame(Quarter.Q4, Quarter.ofMonth(12));
    }
}
