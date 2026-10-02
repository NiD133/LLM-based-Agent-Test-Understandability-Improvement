package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#of(int)} rejects values above the valid range.
 * <p>
 * Valid AM/PM values are 0 (AM) and 1 (PM), so 2 is too high and must be
 * rejected with a {@link DateTimeException}.
 */
public class TestAmPm_test_of_int_valueTooHigh {

    private static final int VALUE_ABOVE_MAX = 2;

    @Test
    public void of_withValueAboveMax_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> AmPm.of(VALUE_ABOVE_MAX));
    }
}
