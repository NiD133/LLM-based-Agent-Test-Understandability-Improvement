package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link RandomUtils#randomFloat(float, float)} rejects an inverted range,
 * i.e. when the lower bound is greater than the upper bound.
 */
public class RandomUtilsTest_testNextFloatLowerGreaterUpper extends AbstractLangTest {

    /**
     * Supplies every {@link RandomUtils} flavour so the validation is checked for each one.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatLowerGreaterUpper(final RandomUtils randomUtils) {
        final float lowerBound = 2;
        final float upperBound = 1;
        // Lower bound exceeds the upper bound, so the call must be rejected.
        assertIllegalArgumentException(() -> randomUtils.randomFloat(lowerBound, upperBound));
    }
}
