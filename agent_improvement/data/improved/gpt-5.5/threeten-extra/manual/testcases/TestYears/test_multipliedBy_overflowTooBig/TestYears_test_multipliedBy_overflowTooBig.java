package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        int yearsJustOverHalfMaxInt = Integer.MAX_VALUE / 2 + 1;
        int doublingScalar = 2;

        assertThrows(
                ArithmeticException.class,
                () -> Years.of(yearsJustOverHalfMaxInt).multipliedBy(doublingScalar));
    }
}
