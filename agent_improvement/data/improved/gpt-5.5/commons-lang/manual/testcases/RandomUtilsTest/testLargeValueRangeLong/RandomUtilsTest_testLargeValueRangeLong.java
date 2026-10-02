package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testLargeValueRangeLong extends AbstractLangTest {

    private static final long LARGE_RANGE_START_INCLUSIVE = 12900000000001L;
    private static final long LARGE_RANGE_END_EXCLUSIVE = 12900000000016L;
    private static final int ATTEMPTS_PER_RANGE_VALUE = 1000;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Regression test for LANG-1592: implementations based on
     * {@link RandomUtils#nextDouble(double, double)} could round up and return
     * the exclusive upper bound for large long ranges.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testLargeValueRangeLong(final RandomUtils randomUtils) {
        final int sampleCount = (int) (LARGE_RANGE_END_EXCLUSIVE - LARGE_RANGE_START_INCLUSIVE) * ATTEMPTS_PER_RANGE_VALUE;

        for (int i = 0; i < sampleCount; i++) {
            assertNotEquals(LARGE_RANGE_END_EXCLUSIVE,
                    randomUtils.randomLong(LARGE_RANGE_START_INCLUSIVE, LARGE_RANGE_END_EXCLUSIVE));
        }
    }
}
