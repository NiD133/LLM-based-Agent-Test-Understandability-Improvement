package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomInt(int, int)} at the largest range it can
 * legally accept: from 0 (inclusive) up to {@link Integer#MAX_VALUE} (exclusive).
 */
public class RandomUtilsTest_testExtremeRangeInt extends AbstractLangTest {

    /**
     * Provides each available {@link RandomUtils} flavor so the test runs once
     * per random-number source.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A value drawn from the full {@code [0, Integer.MAX_VALUE)} range must stay
     * within those bounds for every {@link RandomUtils} flavor.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeInt(final RandomUtils randomUtils) {
        final int result = randomUtils.randomInt(0, Integer.MAX_VALUE);

        assertTrue(result >= 0, "result must be at least the inclusive lower bound 0");
        assertTrue(result < Integer.MAX_VALUE, "result must be below the exclusive upper bound Integer.MAX_VALUE");
    }
}
