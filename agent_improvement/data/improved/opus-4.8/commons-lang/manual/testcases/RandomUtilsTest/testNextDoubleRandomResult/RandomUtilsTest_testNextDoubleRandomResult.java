package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomDouble()} across every flavour of
 * {@link RandomUtils} instance (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextDoubleRandomResult extends AbstractLangTest {

    /**
     * Supplies one {@link RandomUtils} instance per random-number strategy so
     * that the test below runs once for each of them.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * {@link RandomUtils#randomDouble()} must always return a value in its
     * documented range: from 0 (inclusive) up to {@link Double#MAX_VALUE} (exclusive).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleRandomResult(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble();

        assertTrue(result >= 0d, "result should be at least 0");
        assertTrue(result < Double.MAX_VALUE, "result should be below Double.MAX_VALUE");
    }
}
