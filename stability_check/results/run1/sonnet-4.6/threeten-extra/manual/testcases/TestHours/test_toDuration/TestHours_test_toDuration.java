package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Tests that {@link Hours#toDuration()} and its deprecated predecessor {@link Hours#toPeriod()}
 * both produce a {@link Duration} equivalent to the number of hours held by the {@code Hours}
 * instance.
 */
public class TestHours_test_toDuration {

    /**
     * Verifies that {@code toDuration()} returns a {@link Duration} whose hour count equals
     * the value used to create the {@code Hours} instance, covering negative, zero, and
     * positive values.
     */
    @ParameterizedTest(name = "toDuration({0}h) == Duration.ofHours({0})")
    @ValueSource(ints = {-20, -10, -1, 0, 1, 10, 19})
    public void toDuration_returnsEquivalentDuration(int hourCount) {
        Hours hours = Hours.of(hourCount);
        Duration expectedDuration = Duration.ofHours(hourCount);

        assertEquals(expectedDuration, hours.toDuration());
    }

    /**
     * Verifies that the deprecated {@code toPeriod()} method returns the same result as
     * {@code toDuration()}, ensuring backward-compatible behaviour.
     */
    @SuppressWarnings("deprecation")
    @ParameterizedTest(name = "toPeriod({0}h) == Duration.ofHours({0})")
    @ValueSource(ints = {-20, -10, -1, 0, 1, 10, 19})
    public void toPeriod_deprecated_returnsSameResultAsToDuration(int hourCount) {
        Hours hours = Hours.of(hourCount);
        Duration expectedDuration = Duration.ofHours(hourCount);

        assertEquals(expectedDuration, hours.toPeriod());
    }
}
