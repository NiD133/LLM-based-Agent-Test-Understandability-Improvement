package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    /**
     * Provides each of the three {@link RandomUtils} flavours so the test runs
     * once per random source: secure, secure-strong and insecure.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Requesting zero random bytes must return an empty array, regardless of the
     * underlying random source.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils ru) {
        final byte[] zeroBytes = ru.randomBytes(0);

        assertArrayEquals(new byte[0], zeroBytes);
    }
}
