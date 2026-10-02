package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatMinimalRange extends AbstractLangTest {

    /** For comparing doubles and floats. */
    private static final double DELTA = 1e-5;

    private static final float MINIMAL_RANGE_BOUND = 42.1f;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /** Verifies that equal float range bounds return that bound. */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatMinimalRange(final RandomUtils randomUtils) {
        assertEquals(MINIMAL_RANGE_BOUND, randomUtils.randomFloat(MINIMAL_RANGE_BOUND, MINIMAL_RANGE_BOUND), DELTA);
    }
}
