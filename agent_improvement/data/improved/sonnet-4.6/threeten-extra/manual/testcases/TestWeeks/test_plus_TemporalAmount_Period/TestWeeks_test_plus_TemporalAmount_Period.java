package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_zeroWeeks_returnsUnchanged() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(5), fiveWeeks.plus(Period.ofWeeks(0)));
    }

    @Test
    public void test_plus_positiveWeeks_increasesAmount() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(7), fiveWeeks.plus(Period.ofWeeks(2)));
    }

    @Test
    public void test_plus_negativeWeeks_decreasesAmount() {
        Weeks fiveWeeks = Weeks.of(5);
        assertEquals(Weeks.of(3), fiveWeeks.plus(Period.ofWeeks(-2)));
    }

    @Test
    public void test_plus_toMaxValue_doesNotOverflow() {
        assertEquals(Weeks.of(Integer.MAX_VALUE),
                Weeks.of(Integer.MAX_VALUE - 1).plus(Period.ofWeeks(1)));
    }

    @Test
    public void test_plus_toMinValue_doesNotUnderflow() {
        assertEquals(Weeks.of(Integer.MIN_VALUE),
                Weeks.of(Integer.MIN_VALUE + 1).plus(Period.ofWeeks(-1)));
    }
}
