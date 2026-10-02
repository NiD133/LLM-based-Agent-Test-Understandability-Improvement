package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomDouble(double, double)} when the range is minimal,
 * i.e. the inclusive start and exclusive end are equal.
 *
 * <p>In this degenerate case the method has no room to pick a random value and must
 * return the single boundary value itself. The behaviour is verified for every
 * {@link RandomUtils} flavour (secure, secure-strong and insecure).</p>
 */
public class RandomUtilsTest_testNextDoubleMinimalRange extends AbstractLangTest {

    /** Tolerance used when comparing doubles. */
    private static final double DELTA = 1e-5;

    /** The single value used as both the inclusive start and the exclusive end. */
    private static final double BOUNDARY_VALUE = 42.1;

    /**
     * Provides each {@link RandomUtils} flavour so the test runs once per implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleMinimalRange(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(BOUNDARY_VALUE, BOUNDARY_VALUE);

        assertEquals(BOUNDARY_VALUE, result, DELTA);
    }
}
