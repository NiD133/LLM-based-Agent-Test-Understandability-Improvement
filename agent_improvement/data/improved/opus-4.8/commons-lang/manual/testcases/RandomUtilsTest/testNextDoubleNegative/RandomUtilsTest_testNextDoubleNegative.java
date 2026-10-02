package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomDouble(double, double)} rejects a negative
 * lower bound, regardless of which {@link RandomUtils} flavor is used.
 */
public class RandomUtilsTest_testNextDoubleNegative extends AbstractLangTest {

    /** A negative lower bound, which is not allowed. */
    private static final double NEGATIVE_START = -1;

    /** A valid upper bound. */
    private static final double END = 1;

    /**
     * Provides each available {@link RandomUtils} flavor so the test runs once per instance.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleNegative(final RandomUtils ru) {
        // A negative start value must be rejected with IllegalArgumentException.
        assertIllegalArgumentException(() -> ru.randomDouble(NEGATIVE_START, END));
    }
}
