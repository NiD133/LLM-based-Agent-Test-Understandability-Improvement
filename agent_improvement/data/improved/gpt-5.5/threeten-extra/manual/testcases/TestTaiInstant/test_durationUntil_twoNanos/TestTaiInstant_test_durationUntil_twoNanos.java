package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_durationUntil_twoNanos {

    @Test
    public void test_durationUntil_twoNanos() {
        TaiInstant start = TaiInstant.ofTaiSeconds(4, 5);
        TaiInstant end = TaiInstant.ofTaiSeconds(4, 7);

        Duration duration = start.durationUntil(end);

        assertEquals(0, duration.getSeconds());
        assertEquals(2, duration.getNano());
    }
}
