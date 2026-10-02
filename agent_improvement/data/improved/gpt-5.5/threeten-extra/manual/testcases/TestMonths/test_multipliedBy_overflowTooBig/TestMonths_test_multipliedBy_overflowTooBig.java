package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        int justOverHalfOfMaxInt = Integer.MAX_VALUE / 2 + 1;
        int multiplierThatOverflowsInt = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Months.of(justOverHalfOfMaxInt).multipliedBy(multiplierThatOverflowsInt));
    }
}
