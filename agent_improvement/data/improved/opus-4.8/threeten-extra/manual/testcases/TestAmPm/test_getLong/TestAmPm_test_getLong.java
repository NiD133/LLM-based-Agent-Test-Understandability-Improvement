package org.threeten.extra;

import static java.time.temporal.ChronoField.AMPM_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AmPm#getLong(java.time.temporal.TemporalField)} for the
 * {@code AMPM_OF_DAY} field.
 * <p>
 * The numeric value of an am-pm follows {@link java.util.Calendar}: AM maps to 0
 * and PM maps to 1.
 */
public class TestAmPm_test_getLong {

    @Test
    public void getLong_ampmOfDay_returnsCalendarValue() {
        // AM is the morning half-day and has the numeric value 0.
        assertEquals(0, AmPm.AM.getLong(AMPM_OF_DAY));
        // PM is the afternoon half-day and has the numeric value 1.
        assertEquals(1, AmPm.PM.getLong(AMPM_OF_DAY));
    }
}
