package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeLong extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomLong} handles the extreme range [0, Long.MAX_VALUE):
     * the result must be non-negative and strictly less than Long.MAX_VALUE.
     * This is the widest valid range for the method and exercises boundary arithmetic.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeLong(final RandomUtils ru) {
        final long startInclusive = 0;
        final long endExclusive = Long.MAX_VALUE;

        final long result = ru.randomLong(startInclusive, endExclusive);

        assertTrue(result >= startInclusive, "result should be >= 0 (startInclusive)");
        assertTrue(result < endExclusive, "result should be < Long.MAX_VALUE (endExclusive)");
    }
}
