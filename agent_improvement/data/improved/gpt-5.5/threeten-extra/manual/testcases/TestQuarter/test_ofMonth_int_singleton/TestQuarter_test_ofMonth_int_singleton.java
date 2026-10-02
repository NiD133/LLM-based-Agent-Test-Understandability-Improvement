package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_ofMonth_int_singleton {

    @Test
    public void test_ofMonth_int_singleton() {
        // January to March are represented by the Q1 singleton.
        assertSame(Quarter.Q1, Quarter.ofMonth(1));
        assertSame(Quarter.Q1, Quarter.ofMonth(2));
        assertSame(Quarter.Q1, Quarter.ofMonth(3));

        // April to June are represented by the Q2 singleton.
        assertSame(Quarter.Q2, Quarter.ofMonth(4));
        assertSame(Quarter.Q2, Quarter.ofMonth(5));
        assertSame(Quarter.Q2, Quarter.ofMonth(6));

        // July to September are represented by the Q3 singleton.
        assertSame(Quarter.Q3, Quarter.ofMonth(7));
        assertSame(Quarter.Q3, Quarter.ofMonth(8));
        assertSame(Quarter.Q3, Quarter.ofMonth(9));

        // October to December are represented by the Q4 singleton.
        assertSame(Quarter.Q4, Quarter.ofMonth(10));
        assertSame(Quarter.Q4, Quarter.ofMonth(11));
        assertSame(Quarter.Q4, Quarter.ofMonth(12));
    }
}
