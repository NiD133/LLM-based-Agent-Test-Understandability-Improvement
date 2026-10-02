package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_Weeks {

    @Test
    public void test_plus_TemporalAmount_addingZeroWeeks_returnsUnchangedAmount() {
        Weeks base = Weeks.of(5);
        assertEquals(Weeks.of(5), base.plus(Weeks.of(0)));
    }

    @Test
    public void test_plus_TemporalAmount_addingPositiveWeeks_returnsSum() {
        Weeks base = Weeks.of(5);
        assertEquals(Weeks.of(7), base.plus(Weeks.of(2)));
    }

    @Test
    public void test_plus_TemporalAmount_addingNegativeWeeks_returnsDifference() {
        Weeks base = Weeks.of(5);
        assertEquals(Weeks.of(3), base.plus(Weeks.of(-2)));
    }

    @Test
    public void test_plus_TemporalAmount_atUpperBoundary_returnsMaxValue() {
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(Weeks.of(1)));
    }

    @Test
    public void test_plus_TemporalAmount_atLowerBoundary_returnsMinValue() {
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(Weeks.of(-1)));
    }
}
