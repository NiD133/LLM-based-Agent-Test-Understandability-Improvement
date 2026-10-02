package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#from(java.time.temporal.TemporalAccessor)} throws
 * {@link DateTimeException} when the given temporal object cannot provide a
 * half-of-year value and no derivation path exists.
 *
 * <p>{@code LocalTime} carries only time-of-day information and has no concept
 * of a calendar half-year, so {@code Half.from} must reject it.
 */
public class TestHalf_test_from_TemporalAccessorl_invalid_noDerive {

    @Test
    public void test_from_TemporalAccessorl_invalid_noDerive() {
        // LocalTime has no HALF_OF_YEAR field and cannot be converted to LocalDate,
        // so Half.from must throw DateTimeException rather than silently derive a value.
        assertThrows(DateTimeException.class, () -> Half.from(LocalTime.of(12, 30)));
    }
}
