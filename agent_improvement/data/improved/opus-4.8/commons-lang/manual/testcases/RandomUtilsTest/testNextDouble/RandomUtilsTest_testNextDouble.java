package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomDouble(double, double)}.
 */
public class RandomUtilsTest_testNextDouble extends AbstractLangTest {

    /** Inclusive lower bound of the requested random range. */
    private static final double RANGE_START_INCLUSIVE = 33d;

    /** Exclusive upper bound of the requested random range. */
    private static final double RANGE_END_EXCLUSIVE = 42d;

    /**
     * Provides every {@link RandomUtils} flavor so the test runs once per
     * random-number source: {@code secure}, {@code secureStrong} and {@code insecure}.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that a random double generated for the range
     * [{@value #RANGE_START_INCLUSIVE}, {@value #RANGE_END_EXCLUSIVE}) actually
     * falls within that range, regardless of which random source is used.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDouble(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);

        assertTrue(result >= RANGE_START_INCLUSIVE, "result should be at least the inclusive start");
        assertTrue(result < RANGE_END_EXCLUSIVE, "result should be below the exclusive end");
    }
}
