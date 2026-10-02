package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#plus(java.time.temporal.TemporalAmount)}, verifying that adding
 * one {@code Years} amount to another returns the correctly summed {@code Years}.
 */
public class TestYears_test_plus_TemporalAmount_Years {

    @Test
    public void plus_addingZero_returnsSameAmount() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(5), fiveYears.plus(Years.of(0)));
    }

    @Test
    public void plus_addingPositiveAmount_increasesYears() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(7), fiveYears.plus(Years.of(2)));
    }

    @Test
    public void plus_addingNegativeAmount_decreasesYears() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(3), fiveYears.plus(Years.of(-2)));
    }

    @Test
    public void plus_resultAtIntMaxValue_doesNotOverflow() {
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).plus(Years.of(1)));
    }

    @Test
    public void plus_resultAtIntMinValue_doesNotOverflow() {
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).plus(Years.of(-1)));
    }
}
