package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

public class TestHalf_test_ofMonth_int_singleton {

    @Test
    public void test_ofMonth_int_singleton() {
        // Months 1–6 belong to the first half of the year
        assertSame(Half.H1, Half.ofMonth(1));
        assertSame(Half.H1, Half.ofMonth(2));
        assertSame(Half.H1, Half.ofMonth(3));
        assertSame(Half.H1, Half.ofMonth(4));
        assertSame(Half.H1, Half.ofMonth(5));
        assertSame(Half.H1, Half.ofMonth(6));

        // Months 7–12 belong to the second half of the year
        assertSame(Half.H2, Half.ofMonth(7));
        assertSame(Half.H2, Half.ofMonth(8));
        assertSame(Half.H2, Half.ofMonth(9));
        assertSame(Half.H2, Half.ofMonth(10));
        assertSame(Half.H2, Half.ofMonth(11));
        assertSame(Half.H2, Half.ofMonth(12));
    }
}
