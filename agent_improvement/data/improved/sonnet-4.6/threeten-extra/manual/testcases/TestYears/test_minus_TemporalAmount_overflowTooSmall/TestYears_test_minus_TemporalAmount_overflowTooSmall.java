package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would produce a result below Integer.MIN_VALUE
        Years nearMinimum = Years.of(Integer.MIN_VALUE + 1);
        Years amountToSubtract = Years.of(2);
        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(amountToSubtract));
    }
}
