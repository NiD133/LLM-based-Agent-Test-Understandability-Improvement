package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link AmPm#ofHour(int)} rejects an hour-of-day above the valid range (0 to 23).
 */
public class TestAmPm_test_ofHour_int_valueTooHigh {

    private static final int HOUR_ABOVE_MAXIMUM = 24;

    @Test
    public void ofHour_rejectsHourAboveValidRange() {
        assertThrows(DateTimeException.class, () -> AmPm.ofHour(HOUR_ABOVE_MAXIMUM));
    }
}
