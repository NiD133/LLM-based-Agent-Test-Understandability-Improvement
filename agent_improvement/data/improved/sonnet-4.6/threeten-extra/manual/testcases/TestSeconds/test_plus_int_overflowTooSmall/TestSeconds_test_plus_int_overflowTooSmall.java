package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_int_overflowTooSmall {

    @Test
    public void test_plus_int_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would require a result below Integer.MIN_VALUE,
        // which cannot be represented as an int, so ArithmeticException must be thrown.
        int nearMinValue = Integer.MIN_VALUE + 1;
        int amountToAdd = -2;
        assertThrows(ArithmeticException.class, () -> Seconds.of(nearMinValue).plus(amountToAdd));
    }
}
