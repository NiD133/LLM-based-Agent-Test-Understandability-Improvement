package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_int_overflowTooBig {

    /**
     * Subtracting a negative amount adds to the total, so subtracting -2 from
     * (Integer.MAX_VALUE - 1) months pushes the result past Integer.MAX_VALUE
     * and must overflow with an ArithmeticException.
     */
    @Test
    public void test_minus_int_overflowTooBig() {
        Months almostMaxMonths = Months.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> almostMaxMonths.minus(-2));
    }
}
