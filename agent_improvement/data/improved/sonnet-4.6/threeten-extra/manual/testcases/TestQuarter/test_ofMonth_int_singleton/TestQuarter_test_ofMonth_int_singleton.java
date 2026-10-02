package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_singleton {

    @Test
    public void test_ofMonth_int_singleton() {
        // Q1: January (1), February (2), March (3)
        assertSame(Quarter.Q1, Quarter.ofMonth(1));
        assertSame(Quarter.Q1, Quarter.ofMonth(2));
        assertSame(Quarter.Q1, Quarter.ofMonth(3));
        // Q2: April (4), May (5), June (6)
        assertSame(Quarter.Q2, Quarter.ofMonth(4));
        assertSame(Quarter.Q2, Quarter.ofMonth(5));
        assertSame(Quarter.Q2, Quarter.ofMonth(6));
        // Q3: July (7), August (8), September (9)
        assertSame(Quarter.Q3, Quarter.ofMonth(7));
        assertSame(Quarter.Q3, Quarter.ofMonth(8));
        assertSame(Quarter.Q3, Quarter.ofMonth(9));
        // Q4: October (10), November (11), December (12)
        assertSame(Quarter.Q4, Quarter.ofMonth(10));
        assertSame(Quarter.Q4, Quarter.ofMonth(11));
        assertSame(Quarter.Q4, Quarter.ofMonth(12));
    }
}
