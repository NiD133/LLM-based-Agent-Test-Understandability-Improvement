package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_null {

    @Test
    public void minusTemporalAmountRejectsNull() {
        assertThrows(
                NullPointerException.class,
                () -> Months.of(Integer.MIN_VALUE + 1).minus(null));
    }
}
