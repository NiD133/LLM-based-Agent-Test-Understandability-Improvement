package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link RandomUtils#randomDouble(double, double)} rejects a range
 * whose lower bound is greater than its upper bound, regardless of which
 * {@link RandomUtils} flavour (secure, secure-strong or insecure) is used.
 */
public class RandomUtilsTest_testNextDoubleLowerGreaterUpper extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} singletons exercised by this test.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleLowerGreaterUpper(final RandomUtils randomUtils) {
        // Lower bound (2) is greater than the upper bound (1): an invalid range.
        final double lowerBound = 2;
        final double upperBound = 1;

        assertIllegalArgumentException(() -> randomUtils.randomDouble(lowerBound, upperBound));
    }
}
