package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would yield MIN_VALUE - 1, underflowing int range
        Years nearMinimum = Years.of(Integer.MIN_VALUE + 1);
        Years subtractTwo = Years.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(subtractTwo));
    }
}
