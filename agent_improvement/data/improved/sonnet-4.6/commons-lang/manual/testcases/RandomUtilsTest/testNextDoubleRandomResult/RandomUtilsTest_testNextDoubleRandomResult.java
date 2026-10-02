package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleRandomResult extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that randomDouble() returns a value in [0, Double.MAX_VALUE).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleRandomResult(final RandomUtils ru) {
        final double result = ru.randomDouble();
        assertTrue(result >= 0d, "randomDouble() must return a non-negative value");
        assertTrue(result < Double.MAX_VALUE, "randomDouble() must return a value less than Double.MAX_VALUE");
    }
}
