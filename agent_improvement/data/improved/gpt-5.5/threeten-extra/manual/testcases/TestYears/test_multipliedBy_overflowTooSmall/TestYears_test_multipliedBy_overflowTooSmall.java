package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        int yearsJustBelowHalfOfMinimumInteger = Integer.MIN_VALUE / 2 - 1;
        int multiplierThatOverflows = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Years.of(yearsJustBelowHalfOfMinimumInteger).multipliedBy(multiplierThatOverflows));
    }
}
