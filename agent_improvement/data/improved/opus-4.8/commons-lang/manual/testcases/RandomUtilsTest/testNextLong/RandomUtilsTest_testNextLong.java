package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomLong(long, long)} for the three flavours of
 * {@link RandomUtils} (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextLong extends AbstractLangTest {

    /** Inclusive lower bound passed to {@code randomLong}. */
    private static final long RANGE_START_INCLUSIVE = 33L;

    /** Exclusive upper bound passed to {@code randomLong}. */
    private static final long RANGE_END_EXCLUSIVE = 42L;

    /**
     * Supplies each {@link RandomUtils} flavour so the test runs once per implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A long generated within a range must fall inside {@code [start, end)}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLong(final RandomUtils randomUtils) {
        final long result = randomUtils.randomLong(RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);

        assertTrue(result >= RANGE_START_INCLUSIVE, "result should be at least the inclusive start");
        assertTrue(result < RANGE_END_EXCLUSIVE, "result should be below the exclusive end");
    }
}
