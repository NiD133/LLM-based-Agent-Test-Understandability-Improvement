package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    /**
     * Provides each flavor of {@link RandomUtils} so the test runs once per implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Requesting zero random bytes must return an empty array rather than {@code null} or a longer array.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils randomUtils) {
        final byte[] expectedEmptyArray = new byte[0];

        assertArrayEquals(expectedEmptyArray, randomUtils.randomBytes(0));
    }
}
