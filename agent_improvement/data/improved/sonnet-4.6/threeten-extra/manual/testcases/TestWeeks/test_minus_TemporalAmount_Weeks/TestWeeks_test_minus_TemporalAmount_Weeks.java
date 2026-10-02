package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Weeks {

    @Test
    public void test_minus_zero_returnsUnchanged() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(5), fiveWeeks.minus(Weeks.of(0)),
                "Subtracting zero weeks should return an equal instance");
    }

    @Test
    public void test_minus_positiveAmount_reducesWeeks() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(3), fiveWeeks.minus(Weeks.of(2)),
                "5 minus 2 should equal 3");
    }

    @Test
    public void test_minus_negativeAmount_increasesWeeks() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(7), fiveWeeks.minus(Weeks.of(-2)),
                "Subtracting a negative amount should increase the total (5 - (-2) = 7)");
    }

    @Test
    public void test_minus_atMaxBoundary_doesNotOverflow() {
        assertEquals(Weeks.of(Integer.MAX_VALUE),
                Weeks.of(Integer.MAX_VALUE - 1).minus(Weeks.of(-1)),
                "Subtracting -1 from MAX_VALUE-1 should yield MAX_VALUE without overflow");
    }

    @Test
    public void test_minus_atMinBoundary_doesNotOverflow() {
        assertEquals(Weeks.of(Integer.MIN_VALUE),
                Weeks.of(Integer.MIN_VALUE + 1).minus(Weeks.of(1)),
                "Subtracting 1 from MIN_VALUE+1 should yield MIN_VALUE without overflow");
    }
}
