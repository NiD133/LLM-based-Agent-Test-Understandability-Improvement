package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_durationUntil_fifteenSeconds {

    /**
     * Verifies that durationUntil returns a Duration of exactly 15 seconds
     * when the two TAI instants are 15 seconds apart with no nanosecond component.
     */
    @Test
    public void test_durationUntil_fifteenSeconds() {
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(10, 0);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(25, 0);
        Duration duration = tai1.durationUntil(tai2);
        assertEquals(15, duration.getSeconds());
        assertEquals(0, duration.getNano());
    }
}
