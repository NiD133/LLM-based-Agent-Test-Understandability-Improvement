package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomInt(int, int)}.
 *
 * <p>The same assertions are exercised against every flavour of {@link RandomUtils}
 * (secure, secure-strong and insecure) supplied by {@link #randomProvider()}.</p>
 */
public class RandomUtilsTest_testNextInt extends AbstractLangTest {

    /** Lower bound (inclusive) passed to {@code randomInt}. */
    private static final int RANGE_START_INCLUSIVE = 33;

    /** Upper bound (exclusive) passed to {@code randomInt}. */
    private static final int RANGE_END_EXCLUSIVE = 42;

    /**
     * Provides the three {@link RandomUtils} variants that should all behave identically
     * with respect to the bounds of {@code randomInt}.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A value drawn from {@code randomInt(33, 42)} must stay within the requested
     * half-open range {@code [33, 42)}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextInt(final RandomUtils randomUtils) {
        final int result = randomUtils.randomInt(RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);

        assertTrue(result >= RANGE_START_INCLUSIVE, "result should be >= lower bound (inclusive)");
        assertTrue(result < RANGE_END_EXCLUSIVE, "result should be < upper bound (exclusive)");
    }
}
