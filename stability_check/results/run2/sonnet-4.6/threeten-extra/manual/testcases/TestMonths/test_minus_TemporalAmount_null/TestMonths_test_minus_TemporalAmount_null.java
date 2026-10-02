package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_null {

    @Test
    public void test_minus_TemporalAmount_null() {
        // Use a value one above MIN_VALUE to avoid overflow if negation is ever reached
        int baseMonths = Integer.MIN_VALUE + 1;
        Months months = Months.of(baseMonths);

        assertThrows(NullPointerException.class, () -> months.minus(null));
    }
}
