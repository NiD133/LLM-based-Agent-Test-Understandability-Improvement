package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomBytes(int)} across every available
 * {@link RandomUtils} flavour (secure, secure-strong and insecure).
 */
public class RandomUtilsTest_testNextBytes extends AbstractLangTest {

    /** Number of bytes requested in the test below. */
    private static final int REQUESTED_BYTE_COUNT = 20;

    /**
     * Supplies each {@link RandomUtils} variant so the test runs once per flavour.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomBytes(count)} returns an array of exactly the requested length.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytes(final RandomUtils randomUtils) {
        final byte[] generatedBytes = randomUtils.randomBytes(REQUESTED_BYTE_COUNT);

        assertEquals(REQUESTED_BYTE_COUNT, generatedBytes.length);
    }
}
