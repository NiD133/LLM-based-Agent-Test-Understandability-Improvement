package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_minus_overflowTooBig {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long NANOS_PER_DAY = SECS_PER_DAY * NANOS_PER_SEC;

    /**
     * Subtracting a negative duration moves the instant forwards. Starting from the
     * latest representable Modified Julian Day at the last nanosecond of the day,
     * subtracting -1 nanosecond pushes past Long.MAX_VALUE and must overflow.
     */
    @Test
    public void test_minus_overflowTooBig() {
        UtcInstant maxInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);

        assertThrows(ArithmeticException.class, () -> maxInstant.minus(Duration.ofNanos(-1)));
    }
}
