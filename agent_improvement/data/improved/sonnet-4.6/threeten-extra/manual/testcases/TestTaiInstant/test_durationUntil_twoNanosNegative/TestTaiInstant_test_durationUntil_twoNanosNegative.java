package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_durationUntil_twoNanosNegative {

    @Test
    public void test_durationUntil_twoNanosNegative() {
        // tai2 is 2 nanoseconds before tai1 (same second, nanos: 7 vs 9)
        TaiInstant tai1 = TaiInstant.ofTaiSeconds(4, 9);
        TaiInstant tai2 = TaiInstant.ofTaiSeconds(4, 7);

        Duration test = tai1.durationUntil(tai2);

        // Duration normalizes -2ns as: -1 second + 999_999_998 nanoseconds
        assertEquals(-1, test.getSeconds());
        assertEquals(999999998, test.getNano());
    }
}
