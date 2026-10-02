package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> {
            Weeks nearMinimumWeeks = Weeks.of(Integer.MIN_VALUE + 1);
            Weeks twoNegativeWeeks = Weeks.of(-2);

            nearMinimumWeeks.plus(twoNegativeWeeks);
        });
    }
}
