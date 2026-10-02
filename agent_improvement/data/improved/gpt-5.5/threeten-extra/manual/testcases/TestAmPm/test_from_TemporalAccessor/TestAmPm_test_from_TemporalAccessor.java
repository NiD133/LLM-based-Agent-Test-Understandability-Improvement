package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_from_TemporalAccessor {

    @Test
    public void test_from_TemporalAccessor() {
        LocalTime morningTime = LocalTime.of(8, 30);
        LocalTime afternoonTime = LocalTime.of(17, 30);

        assertEquals(AmPm.AM, AmPm.from(morningTime));
        assertEquals(AmPm.PM, AmPm.from(afternoonTime));
    }
}
