package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_from_TemporalAccessor {

    // AM spans midnight to just before noon (00:00–11:59)
    @Test
    public void test_from_TemporalAccessor_morning_returnsAM() {
        LocalTime morningTime = LocalTime.of(8, 30);
        assertEquals(AmPm.AM, AmPm.from(morningTime));
    }

    // PM spans noon to just before midnight (12:00–23:59)
    @Test
    public void test_from_TemporalAccessor_afternoon_returnsPM() {
        LocalTime afternoonTime = LocalTime.of(17, 30);
        assertEquals(AmPm.PM, AmPm.from(afternoonTime));
    }
}
