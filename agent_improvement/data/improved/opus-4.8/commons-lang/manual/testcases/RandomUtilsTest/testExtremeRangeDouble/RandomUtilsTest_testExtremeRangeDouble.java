package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link RandomUtils#randomDouble(double, double)} behaves correctly
 * when handed the widest legal range: from {@code 0} up to {@link Double#MAX_VALUE}.
 * <p>
 * The same scenario is exercised against every {@link RandomUtils} flavour
 * (secure, secure-strong and insecure) so that all three share the boundary guarantee.
 * </p>
 */
public class RandomUtilsTest_testExtremeRangeDouble extends AbstractLangTest {

    /** The lower bound of the range under test (inclusive). */
    private static final double RANGE_START_INCLUSIVE = 0;

    /** The upper bound of the range under test (exclusive in the API, inclusive in this assertion). */
    private static final double RANGE_END = Double.MAX_VALUE;

    /**
     * Supplies the three {@link RandomUtils} implementations that should all satisfy the contract.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeDouble(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(RANGE_START_INCLUSIVE, RANGE_END);

        // The generated value must stay within the requested [0, Double.MAX_VALUE] range.
        assertTrue(result >= RANGE_START_INCLUSIVE && result <= RANGE_END,
                "Expected a value within [0, Double.MAX_VALUE] but was " + result);
    }
}
