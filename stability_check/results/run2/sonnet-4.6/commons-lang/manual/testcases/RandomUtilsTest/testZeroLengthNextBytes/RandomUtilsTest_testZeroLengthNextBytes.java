package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Tests that requesting zero random bytes returns an empty byte array.
     * Verifies that all RandomUtils implementations (secure, secureStrong, insecure)
     * correctly handle a zero-length request without throwing an exception.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils randomUtils) {
        final byte[] result = randomUtils.randomBytes(0);
        assertArrayEquals(new byte[0], result, "randomBytes(0) should return an empty byte array");
    }
}
