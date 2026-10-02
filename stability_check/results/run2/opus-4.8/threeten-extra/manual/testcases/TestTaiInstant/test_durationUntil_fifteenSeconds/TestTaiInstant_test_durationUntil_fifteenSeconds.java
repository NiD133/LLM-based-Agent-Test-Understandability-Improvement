package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link TaiInstant#durationUntil(TaiInstant)} for two instants that
 * are a whole number of seconds apart.
 */
public class TestTaiInstant_test_durationUntil_fifteenSeconds {

    @Test
    public void durationUntil_returnsFifteenSecondGap() {
        TaiInstant start = TaiInstant.ofTaiSeconds(10, 0);
        TaiInstant end = TaiInstant.ofTaiSeconds(25, 0);

        Duration gap = start.durationUntil(end);

        assertEquals(15, gap.getSeconds(), "seconds between the two TAI instants");
        assertEquals(0, gap.getNano(), "nanosecond fraction of the gap");
    }
}
