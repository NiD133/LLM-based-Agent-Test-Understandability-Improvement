package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link TaiInstant#withTaiSeconds(long)}.
 * <p>
 * {@code withTaiSeconds} returns a copy of the instant with the seconds replaced,
 * leaving the nanosecond-of-second untouched.
 */
public class TestTaiInstant_test_withTAISeconds {

    /**
     * Cases for {@link #test_withTAISeconds}.
     * <p>
     * Each case is: (initial seconds, initial nanos, replacement seconds,
     * expected seconds after replacement, expected nanos after replacement).
     */
    public static Object[][] data_withTAISeconds() {
        return new Object[][] {
            //  initial   initial   replacement   expected   expected
            //  seconds   nanos     seconds       seconds    nanos
            { 0L,   12345L,  1L,  1L,  12345L },
            { 0L,   12345L, -1L, -1L,  12345L },
            { 7L,   12345L,  2L,  2L,  12345L },
            { 7L,   12345L, -2L, -2L,  12345L },
            { -99L, 12345L,  3L,  3L,  12345L },
            { -99L, 12345L, -3L, -3L,  12345L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withTAISeconds")
    public void test_withTAISeconds(
            long initialSeconds,
            long initialNanos,
            long replacementSeconds,
            Long expectedSeconds,
            Long expectedNanos) {

        TaiInstant result = TaiInstant.ofTaiSeconds(initialSeconds, initialNanos)
                .withTaiSeconds(replacementSeconds);

        assertEquals(expectedSeconds.longValue(), result.getTaiSeconds());
        assertEquals(expectedNanos.longValue(), result.getNano());
    }
}
