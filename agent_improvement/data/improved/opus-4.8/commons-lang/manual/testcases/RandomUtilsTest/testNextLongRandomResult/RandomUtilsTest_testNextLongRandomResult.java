package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomLong()} across every kind of {@link RandomUtils}
 * instance (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextLongRandomResult extends AbstractLangTest {

    /**
     * Supplies one {@link RandomUtils} instance of each available flavour so the
     * test below runs once per flavour.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * {@link RandomUtils#randomLong()} must return a value in the range
     * {@code [0, Long.MAX_VALUE)} — i.e. zero or positive, and strictly below
     * {@link Long#MAX_VALUE}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongRandomResult(final RandomUtils randomUtils) {
        final long result = randomUtils.randomLong();

        assertTrue(result >= 0L, "randomLong() must be non-negative");
        assertTrue(result < Long.MAX_VALUE, "randomLong() must be strictly less than Long.MAX_VALUE");
    }
}
