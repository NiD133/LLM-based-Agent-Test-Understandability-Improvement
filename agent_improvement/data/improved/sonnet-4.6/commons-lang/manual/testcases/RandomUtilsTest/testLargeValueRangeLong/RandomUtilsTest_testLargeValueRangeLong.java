package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Regression test for LANG-1592: verifies that {@code randomLong(startInclusive, endExclusive)}
 * never returns a value equal to {@code endExclusive}, even for large long ranges where a
 * double-cast implementation could overflow and produce the upper bound.
 */
public class RandomUtilsTest_testLargeValueRangeLong extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomLong} never returns the exclusive upper bound when the range
     * values are large longs (LANG-1592).
     *
     * <p>A prior implementation used {@code (long) nextDouble(startInclusive, endExclusive)},
     * which can produce a value equal to {@code endExclusive} due to floating-point precision
     * loss. This loop exercises enough iterations to reliably expose the defect.</p>
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testLargeValueRangeLong(final RandomUtils ru) {
        final long startInclusive = 12900000000001L;
        final long endExclusive  = 12900000000016L;
        final long rangeSize     = endExclusive - startInclusive; // 15
        // 1000 iterations per value in the range reliably triggers the bug in the
        // double-cast implementation while staying fast for the correct implementation.
        final int iterations = (int) rangeSize * 1000;
        for (int i = 0; i < iterations; i++) {
            assertNotEquals(
                endExclusive,
                ru.randomLong(startInclusive, endExclusive),
                "randomLong must never return the exclusive upper bound"
            );
        }
    }
}
