package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_overflowTooBig {

    private static final int ONE_WEEK_BELOW_MAX = Integer.MAX_VALUE - 1;
    private static final int TWO_NEGATIVE_WEEKS = -2;

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Weeks almostMaxWeeks = Weeks.of(ONE_WEEK_BELOW_MAX);
        Weeks negativeWeeksToSubtract = Weeks.of(TWO_NEGATIVE_WEEKS);

        assertThrows(
                ArithmeticException.class,
                () -> almostMaxWeeks.minus(negativeWeeksToSubtract));
    }
}
