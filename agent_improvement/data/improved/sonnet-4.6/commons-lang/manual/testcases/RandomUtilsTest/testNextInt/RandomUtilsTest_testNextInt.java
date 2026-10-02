package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextInt extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Tests that randomInt(startInclusive, endExclusive) returns a value within [startInclusive, endExclusive).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextInt(final RandomUtils ru) {
        final int startInclusive = 33;
        final int endExclusive = 42;

        final int result = ru.randomInt(startInclusive, endExclusive);

        assertTrue(result >= startInclusive, "Result should be >= startInclusive (33)");
        assertTrue(result < endExclusive, "Result should be < endExclusive (42)");
    }
}
