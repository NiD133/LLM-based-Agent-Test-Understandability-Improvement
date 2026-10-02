package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomFloat(float, float)} for each kind of
 * {@link RandomUtils} instance (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextFloat extends AbstractLangTest {

    /** Inclusive lower bound passed to {@link RandomUtils#randomFloat(float, float)}. */
    private static final float RANGE_START_INCLUSIVE = 33f;

    /** Exclusive upper bound passed to {@link RandomUtils#randomFloat(float, float)}. */
    private static final float RANGE_END_EXCLUSIVE = 42f;

    /**
     * Supplies the three {@link RandomUtils} flavours so that each test case runs
     * against every randomness source.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A float generated within [33, 42) must fall inside that range:
     * at or above the inclusive start and strictly below the exclusive end.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloat(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);

        assertTrue(result >= RANGE_START_INCLUSIVE, "result should be at or above the inclusive start");
        assertTrue(result < RANGE_END_EXCLUSIVE, "result should be below the exclusive end");
    }
}
