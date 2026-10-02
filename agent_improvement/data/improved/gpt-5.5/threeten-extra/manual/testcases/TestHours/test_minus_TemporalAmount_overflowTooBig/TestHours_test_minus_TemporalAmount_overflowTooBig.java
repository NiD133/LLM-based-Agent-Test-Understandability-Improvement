package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Hours almostMaximumHours = Hours.of(Integer.MAX_VALUE - 1);
        Hours negativeHoursToSubtract = Hours.of(-2);

        assertThrows(ArithmeticException.class, () -> almostMaximumHours.minus(negativeHoursToSubtract));
    }
}
