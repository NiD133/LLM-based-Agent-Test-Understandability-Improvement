package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextBytes extends AbstractLangTest {

    private static final int REQUESTED_BYTE_COUNT = 20;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextBytes(final RandomUtils randomUtils) {
        final byte[] result = randomUtils.randomBytes(REQUESTED_BYTE_COUNT);

        assertEquals(REQUESTED_BYTE_COUNT, result.length);
    }
}
