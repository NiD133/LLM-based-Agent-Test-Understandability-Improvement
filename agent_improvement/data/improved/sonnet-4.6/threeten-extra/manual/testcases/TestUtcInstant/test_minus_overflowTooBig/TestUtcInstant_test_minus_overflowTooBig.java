package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.time.Duration;
import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_minus_overflowTooBig {

    // Total nanoseconds in a standard (non-leap) day: 86_400_000_000_000
    private static final long NANOS_PER_DAY = 24L * 60 * 60 * 1_000_000_000L;

    /**
     * Verifies that subtracting a negative duration from a UtcInstant positioned
     * at the maximum possible MJD day throws ArithmeticException due to overflow.
     * Subtracting Duration.ofNanos(-1) is equivalent to adding 1 nanosecond, which
     * would push the result past Long.MAX_VALUE days.
     */
    @Test
    public void test_minus_overflowTooBig() {
        UtcInstant maxDayInstant = UtcInstant.ofModifiedJulianDay(Long.MAX_VALUE, NANOS_PER_DAY - 1);
        assertThrows(ArithmeticException.class, () -> maxDayInstant.minus(Duration.ofNanos(-1)));
    }
}
