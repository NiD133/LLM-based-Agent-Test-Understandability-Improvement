package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#from(TemporalAmount)} rejects a {@code null} amount.
 */
public class TestMonths_test_from_null {

    @Test
    public void from_nullTemporalAmount_throwsNullPointerException() {
        // Months.from(...) must reject null rather than return a value or throw a different exception.
        assertThrows(
                NullPointerException.class,
                () -> Months.from((TemporalAmount) null));
    }
}
