package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testLargeValueRangeLong extends AbstractLangTest {

    /**
     * Provides each flavour of {@link RandomUtils} so the test runs once per
     * random-number source.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@link RandomUtils#randomLong(long, long)} never returns the
     * exclusive upper bound, even for large values.
     *
     * <p>A previous implementation computed the result as
     * {@code (long) nextDouble(startInclusive, endExclusive)}, which could round
     * up and occasionally yield a value equal to {@code endExclusive}. The loop
     * below repeats the call many times to make that rare failure reliably
     * observable. See LANG-1592.</p>
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testLargeValueRangeLong(final RandomUtils ru) {
        final long startInclusive = 12900000000001L;
        final long endExclusive = 12900000000016L;

        // The buggy implementation only failed sporadically, so we sample 1000
        // draws per value in the range to give it ample opportunity to fail.
        final int rangeSize = (int) (endExclusive - startInclusive);
        final int sampleCount = rangeSize * 1000;

        for (int i = 0; i < sampleCount; i++) {
            assertNotEquals(endExclusive, ru.randomLong(startInclusive, endExclusive));
        }
    }
}
