package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#multipliedBy(int)} reports integer overflow.
 */
public class TestDays_test_multipliedBy_overflowTooBig {

    /**
     * Multiplying a value just above half of {@code Integer.MAX_VALUE} by 2
     * exceeds the int range, so an {@link ArithmeticException} must be thrown.
     */
    @Test
    public void test_multipliedBy_overflowTooBig() {
        Days justOverHalfMaxInt = Days.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> justOverHalfMaxInt.multipliedBy(2));
    }
}
