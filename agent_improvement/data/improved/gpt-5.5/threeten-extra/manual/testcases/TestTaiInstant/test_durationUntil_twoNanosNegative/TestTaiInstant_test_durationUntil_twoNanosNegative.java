package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_durationUntil_twoNanosNegative {

    @Test
    public void test_durationUntil_twoNanosNegative() {
        TaiInstant start = TaiInstant.ofTaiSeconds(4, 9);
        TaiInstant twoNanosEarlier = TaiInstant.ofTaiSeconds(4, 7);

        Duration test = start.durationUntil(twoNanosEarlier);

        assertEquals(-1, test.getSeconds());
        assertEquals(999999998, test.getNano());
    }
}
