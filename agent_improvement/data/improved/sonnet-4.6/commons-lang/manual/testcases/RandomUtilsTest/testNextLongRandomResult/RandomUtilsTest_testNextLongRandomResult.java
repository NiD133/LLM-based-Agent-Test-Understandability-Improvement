package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextLongRandomResult extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@link RandomUtils#randomLong()} returns a value in the half-open range
     * [0, Long.MAX_VALUE), i.e. non-negative and strictly less than {@link Long#MAX_VALUE}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongRandomResult(final RandomUtils ru) {
        final long result = ru.randomLong();
        assertTrue(result >= 0L, "randomLong() must return a non-negative value");
        assertTrue(result < Long.MAX_VALUE, "randomLong() must return a value strictly less than Long.MAX_VALUE");
    }
}
