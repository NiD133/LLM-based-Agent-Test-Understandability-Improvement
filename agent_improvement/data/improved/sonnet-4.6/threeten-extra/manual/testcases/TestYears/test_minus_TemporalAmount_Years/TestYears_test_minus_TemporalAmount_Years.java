package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_Years {

    // Base value used across subtraction scenarios
    private static final Years FIVE_YEARS = Years.of(5);

    @Test
    public void minus_zero_returnsUnchangedAmount() {
        assertEquals(Years.of(5), FIVE_YEARS.minus(Years.of(0)));
    }

    @Test
    public void minus_positiveAmount_decreasesValue() {
        assertEquals(Years.of(3), FIVE_YEARS.minus(Years.of(2)));
    }

    @Test
    public void minus_negativeAmount_increasesValue() {
        // Subtracting a negative is equivalent to adding its absolute value
        assertEquals(Years.of(7), FIVE_YEARS.minus(Years.of(-2)));
    }

    @Test
    public void minus_negativeOne_fromMaxMinusOne_yieldsMaxValue() {
        // (MAX_VALUE - 1) - (-1) = MAX_VALUE — boundary: should not overflow
        assertEquals(Years.of(Integer.MAX_VALUE),
                Years.of(Integer.MAX_VALUE - 1).minus(Years.of(-1)));
    }

    @Test
    public void minus_positiveOne_fromMinPlusOne_yieldsMinValue() {
        // (MIN_VALUE + 1) - 1 = MIN_VALUE — boundary: should not overflow
        assertEquals(Years.of(Integer.MIN_VALUE),
                Years.of(Integer.MIN_VALUE + 1).minus(Years.of(1)));
    }
}
