package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Integer.MIN_VALUE + 1 is the smallest value that is one step above the int minimum.
        // Adding -2 weeks pushes the result below Integer.MIN_VALUE, causing arithmetic overflow.
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);
        Weeks negativeTwo = Weeks.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(negativeTwo));
    }
}
