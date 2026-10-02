package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#ofHour(int)} when the hour-of-day is below the valid range.
 */
public class TestAmPm_test_ofHour_int_valueTooLow {

    /**
     * An hour-of-day of -1 is below the valid range (0 to 23),
     * so {@code ofHour} must reject it with a {@link DateTimeException}.
     */
    @Test
    public void test_ofHour_int_valueTooLow() {
        int hourBelowValidRange = -1;

        assertThrows(DateTimeException.class, () -> AmPm.ofHour(hourBelowValidRange));
    }
}
