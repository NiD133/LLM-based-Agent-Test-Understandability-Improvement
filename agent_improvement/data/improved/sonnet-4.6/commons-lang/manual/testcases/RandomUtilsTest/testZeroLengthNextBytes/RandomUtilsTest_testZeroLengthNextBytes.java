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
     * Verifies that requesting zero bytes returns an empty array, not null,
     * for all three RandomUtils instances (secure, secureStrong, insecure).
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testZeroLengthNextBytes(final RandomUtils ru) {
        assertArrayEquals(new byte[0], ru.randomBytes(0));
    }
}
