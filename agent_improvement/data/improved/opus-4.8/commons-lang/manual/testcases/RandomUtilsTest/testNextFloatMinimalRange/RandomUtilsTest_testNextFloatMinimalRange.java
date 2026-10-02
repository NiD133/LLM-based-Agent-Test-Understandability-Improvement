package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatMinimalRange extends AbstractLangTest {

    /** Tolerance used when comparing float results. */
    private static final double FLOAT_COMPARISON_DELTA = 1e-5;

    /** The single value that serves as both the inclusive start and exclusive end of the range. */
    private static final float SINGLE_POINT_VALUE = 42.1f;

    /**
     * Provides each {@link RandomUtils} flavour (secure, secure-strong and insecure)
     * so the test runs once per random source.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When the start and end of the range are identical, {@code randomFloat}
     * must return exactly that single value.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatMinimalRange(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(SINGLE_POINT_VALUE, SINGLE_POINT_VALUE);

        assertEquals(SINGLE_POINT_VALUE, result, FLOAT_COMPARISON_DELTA);
    }
}
