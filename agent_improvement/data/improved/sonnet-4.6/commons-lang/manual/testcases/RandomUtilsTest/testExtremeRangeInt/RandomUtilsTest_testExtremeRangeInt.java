package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeInt extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that randomInt(0, Integer.MAX_VALUE) returns a value within the
     * extreme range [0, Integer.MAX_VALUE), covering secure, secureStrong, and
     * insecure RandomUtils implementations.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeInt(final RandomUtils ru) {
        final int result = ru.randomInt(0, Integer.MAX_VALUE);
        assertTrue(result >= 0, "Result must be >= 0 (inclusive lower bound)");
        assertTrue(result < Integer.MAX_VALUE, "Result must be < Integer.MAX_VALUE (exclusive upper bound)");
    }
}
