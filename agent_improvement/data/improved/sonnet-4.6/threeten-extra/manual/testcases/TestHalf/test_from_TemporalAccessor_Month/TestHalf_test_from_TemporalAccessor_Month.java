package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestHalf_test_from_TemporalAccessor_Month {

    @Test
    public void test_from_TemporalAccessor_Month() {
        // January through June belong to H1
        assertEquals(Half.H1, Half.from(Month.JANUARY));
        assertEquals(Half.H1, Half.from(Month.FEBRUARY));
        assertEquals(Half.H1, Half.from(Month.MARCH));
        assertEquals(Half.H1, Half.from(Month.APRIL));
        assertEquals(Half.H1, Half.from(Month.MAY));
        assertEquals(Half.H1, Half.from(Month.JUNE));

        // July through December belong to H2
        assertEquals(Half.H2, Half.from(Month.JULY));
        assertEquals(Half.H2, Half.from(Month.AUGUST));
        assertEquals(Half.H2, Half.from(Month.SEPTEMBER));
        assertEquals(Half.H2, Half.from(Month.OCTOBER));
        assertEquals(Half.H2, Half.from(Month.NOVEMBER));
        assertEquals(Half.H2, Half.from(Month.DECEMBER));

        // A Half instance converts to itself
        assertEquals(Half.H1, Half.from(Half.H1));
        assertEquals(Half.H2, Half.from(Half.H2));
    }
}
