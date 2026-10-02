package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    // Subtracting a negative-second duration with positive nanos from the maximum TAI instant
    // causes the nanosecond carry to push the second count past Long.MAX_VALUE, triggering overflow.
    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        Duration durationThatCausesOverflow = Duration.ofSeconds(-1, 999999999);
        assertThrows(ArithmeticException.class, () -> maxInstant.minus(durationThatCausesOverflow));
    }
}
