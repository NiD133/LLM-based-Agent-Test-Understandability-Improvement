package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomFloat()} across every kind of underlying
 * random source (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextFloatRandomResult extends AbstractLangTest {

    /**
     * Supplies one {@link RandomUtils} instance per random-source flavor so the
     * test below runs once for each of them.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A no-argument {@code randomFloat()} must return a value inside its
     * documented range: from 0 (inclusive) up to {@link Float#MAX_VALUE} (exclusive).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatRandomResult(final RandomUtils randomUtils) {
        final float result = randomUtils.randomFloat();

        assertTrue(result >= 0f, "result should be non-negative");
        assertTrue(result < Float.MAX_VALUE, "result should be below Float.MAX_VALUE");
    }
}
