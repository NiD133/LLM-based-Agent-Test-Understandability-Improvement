package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_Period {

    @Test
    public void test_plus_TemporalAmount_Period() {
        Weeks fiveWeeks = Weeks.of(5);

        assertEquals(Weeks.of(5), fiveWeeks.plus(Period.ofWeeks(0)));
        assertEquals(Weeks.of(7), fiveWeeks.plus(Period.ofWeeks(2)));
        assertEquals(Weeks.of(3), fiveWeeks.plus(Period.ofWeeks(-2)));

        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(Period.ofWeeks(1)));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(Period.ofWeeks(-1)));
    }
}
