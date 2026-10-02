package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#minus(TemporalAmount)} rejects a {@code null} argument.
 */
public class TestMonths_test_minus_TemporalAmount_null {

    @Test
    public void minus_withNullAmount_throwsNullPointerException() {
        Months months = Months.of(Integer.MIN_VALUE + 1);

        assertThrows(NullPointerException.class, () -> months.minus((TemporalAmount) null));
    }
}
