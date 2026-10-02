package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Weeks almostMaximumWeeks = Weeks.of(Integer.MAX_VALUE - 1);
        Weeks weeksThatOverflowInt = Weeks.of(2);

        assertThrows(ArithmeticException.class, () -> almostMaximumWeeks.plus(weeksThatOverflowInt));
    }
}
