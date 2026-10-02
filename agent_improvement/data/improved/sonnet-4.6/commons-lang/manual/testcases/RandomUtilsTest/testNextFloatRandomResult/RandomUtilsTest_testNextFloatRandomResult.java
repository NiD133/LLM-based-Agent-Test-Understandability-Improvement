package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatRandomResult extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that randomFloat() returns a value in the range [0, Float.MAX_VALUE)
     * across all three RandomUtils instances (secure, secureStrong, and insecure).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatRandomResult(final RandomUtils ru) {
        final float result = ru.randomFloat();
        assertTrue(result >= 0f, "randomFloat() result must be non-negative, but was: " + result);
        assertTrue(result < Float.MAX_VALUE, "randomFloat() result must be less than Float.MAX_VALUE, but was: " + result);
    }
}
