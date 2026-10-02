package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDouble extends AbstractLangTest {

    private static final double RANGE_START = 33d;
    private static final double RANGE_END = 42d;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that randomDouble returns a value within [startInclusive, endExclusive).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDouble(final RandomUtils ru) {
        final double result = ru.randomDouble(RANGE_START, RANGE_END);
        assertTrue(result >= RANGE_START);
        assertTrue(result < RANGE_END);
    }
}
