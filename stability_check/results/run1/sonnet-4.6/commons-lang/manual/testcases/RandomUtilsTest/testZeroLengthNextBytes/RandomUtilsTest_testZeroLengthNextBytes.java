package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    /**
     * Provides all three RandomUtils variants (secure, secureStrong, insecure)
     * so that the zero-length behaviour is verified across every implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that requesting zero random bytes returns an empty array rather
     * than null or throwing an exception, for every RandomUtils implementation.
     */
    @ParameterizedTest(name = "{0}")
    @MethodSource("randomProvider")
    @DisplayName("randomBytes(0) should return an empty byte array")
    void testZeroLengthNextBytes(final RandomUtils ru) {
        assertArrayEquals(new byte[0], ru.randomBytes(0),
                "randomBytes(0) must return an empty byte array, not null or a non-empty array");
    }
}
