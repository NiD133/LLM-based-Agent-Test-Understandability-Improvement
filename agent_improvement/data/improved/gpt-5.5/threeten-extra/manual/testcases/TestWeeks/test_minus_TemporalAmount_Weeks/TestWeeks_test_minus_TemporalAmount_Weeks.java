package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Weeks {

    @Test
    public void test_minus_TemporalAmount_Weeks() {
        Weeks test5 = Weeks.of(5);

        assertEquals(Weeks.of(5), test5.minus(Weeks.of(0)));
        assertEquals(Weeks.of(3), test5.minus(Weeks.of(2)));
        assertEquals(Weeks.of(7), test5.minus(Weeks.of(-2)));

        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).minus(Weeks.of(-1)));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).minus(Weeks.of(1)));
    }
}
