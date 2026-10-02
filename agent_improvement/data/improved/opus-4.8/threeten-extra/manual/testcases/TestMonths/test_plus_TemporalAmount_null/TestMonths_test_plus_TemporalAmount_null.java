package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#plus(TemporalAmount)} rejects a {@code null} argument.
 */
public class TestMonths_test_plus_TemporalAmount_null {

    @Test
    public void plus_null_throwsNullPointerException() {
        Months months = Months.of(Integer.MIN_VALUE + 1);

        // Adding a null TemporalAmount is not allowed.
        assertThrows(NullPointerException.class, () -> months.plus((TemporalAmount) null));
    }
}
