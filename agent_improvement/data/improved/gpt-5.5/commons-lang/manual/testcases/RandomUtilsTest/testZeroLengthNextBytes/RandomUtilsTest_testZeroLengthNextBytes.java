package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testZeroLengthNextBytes extends AbstractLangTest {

    private static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testZeroLengthNextBytes(final RandomUtils randomUtils) {
        assertArrayEquals(new byte[0], randomUtils.randomBytes(0));
    }
}
