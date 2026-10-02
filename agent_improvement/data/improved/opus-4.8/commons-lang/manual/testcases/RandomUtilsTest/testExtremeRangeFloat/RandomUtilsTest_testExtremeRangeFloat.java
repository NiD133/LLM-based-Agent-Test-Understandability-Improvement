package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomFloat(float, float)} over the widest possible
 * range, exercising every {@link RandomUtils} flavour.
 */
public class RandomUtilsTest_testExtremeRangeFloat extends AbstractLangTest {

    /**
     * Provides the three {@link RandomUtils} variants so each test runs once per flavour.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A random float drawn from the full [0, Float.MAX_VALUE] range must stay within that range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeFloat(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat(0, Float.MAX_VALUE);

        // TODO: should the upper bound be exclusive (< max)?
        assertTrue(result >= 0f && result <= Float.MAX_VALUE,
            "Result " + result + " must lie within [0, Float.MAX_VALUE]");
    }
}
