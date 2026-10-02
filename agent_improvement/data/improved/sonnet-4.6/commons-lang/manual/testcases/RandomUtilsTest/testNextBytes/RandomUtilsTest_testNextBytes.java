package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextBytes extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomBytes returns an array of exactly the requested size")
    void testNextBytes(final RandomUtils randomUtils) {
        final int requestedSize = 20;
        final byte[] result = randomUtils.randomBytes(requestedSize);
        assertEquals(requestedSize, result.length,
                "randomBytes(" + requestedSize + ") should return an array of length " + requestedSize);
    }
}
