package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatRandomResult extends AbstractLangTest {

    private static final float MINIMUM_RANDOM_FLOAT = 0f;
    private static final float MAXIMUM_RANDOM_FLOAT = Float.MAX_VALUE;

    static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Tests next float range, random result.
     */
    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testNextFloatRandomResult(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat();

        assertTrue(result >= MINIMUM_RANDOM_FLOAT);
        assertTrue(result < MAXIMUM_RANDOM_FLOAT);
    }
}
