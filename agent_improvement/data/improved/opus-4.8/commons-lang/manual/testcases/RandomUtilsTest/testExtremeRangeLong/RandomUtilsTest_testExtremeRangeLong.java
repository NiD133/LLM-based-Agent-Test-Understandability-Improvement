package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeLong extends AbstractLangTest {

    /**
     * Supplies the three {@link RandomUtils} flavours so each test runs against all of them.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomLong} can handle the widest legal range, [0, Long.MAX_VALUE),
     * and that the generated value always falls inside that range.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeLong(final RandomUtils ru) {
        final long result = ru.randomLong(0, Long.MAX_VALUE);

        assertTrue(result >= 0, "result should be >= 0 (inclusive lower bound)");
        assertTrue(result < Long.MAX_VALUE, "result should be < Long.MAX_VALUE (exclusive upper bound)");
    }
}
