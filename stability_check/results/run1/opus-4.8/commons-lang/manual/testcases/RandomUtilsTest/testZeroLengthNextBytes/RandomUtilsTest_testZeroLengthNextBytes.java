package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link RandomUtils#randomBytes(int)} when asked for a zero-length array.
 */
public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    /**
     * Supplies every {@link RandomUtils} variant so the test runs once per implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Requesting zero random bytes must return an empty byte array.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils randomUtils) {
        final byte[] emptyResult = randomUtils.randomBytes(0);

        assertArrayEquals(new byte[0], emptyResult);
    }
}
