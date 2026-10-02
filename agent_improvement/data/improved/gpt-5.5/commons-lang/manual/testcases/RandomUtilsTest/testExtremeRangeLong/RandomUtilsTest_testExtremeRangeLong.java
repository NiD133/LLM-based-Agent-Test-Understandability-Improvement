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
     * Tests the widest supported long range: zero inclusive to Long.MAX_VALUE exclusive.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeLong(final RandomUtils randomUtils) {
        final long generatedLong = randomUtils.randomLong(0, Long.MAX_VALUE);

        assertTrue(generatedLong >= 0);
        assertTrue(generatedLong < Long.MAX_VALUE);
    }
}
