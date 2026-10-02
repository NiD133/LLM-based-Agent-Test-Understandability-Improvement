package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("RandomUtils - randomLong(startInclusive, endExclusive)")
public class RandomUtilsTest_testNextLong extends AbstractLangTest {

    private static final long RANGE_START_INCLUSIVE = 33L;
    private static final long RANGE_END_EXCLUSIVE = 42L;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("result is within [startInclusive, endExclusive)")
    void testNextLong(final RandomUtils ru) {
        final long result = ru.randomLong(RANGE_START_INCLUSIVE, RANGE_END_EXCLUSIVE);
        assertTrue(result >= RANGE_START_INCLUSIVE);
        assertTrue(result < RANGE_END_EXCLUSIVE);
    }
}
