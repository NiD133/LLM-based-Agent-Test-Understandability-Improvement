package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeFloat extends AbstractLangTest {

    private static final float MINIMUM_RESULT = 0f;
    private static final float MAXIMUM_RESULT = Float.MAX_VALUE;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeFloat(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(MINIMUM_RESULT, MAXIMUM_RESULT);

        assertTrue(isWithinExtremeFloatRange(result));
    }

    private static boolean isWithinExtremeFloatRange(final float value) {
        return value >= MINIMUM_RESULT && value <= MAXIMUM_RESULT;
    }
}
