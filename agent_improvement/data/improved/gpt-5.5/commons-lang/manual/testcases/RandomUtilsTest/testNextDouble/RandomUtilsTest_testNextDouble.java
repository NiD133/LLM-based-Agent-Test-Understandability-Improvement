package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDouble extends AbstractLangTest {

    static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testNextDouble(final RandomUtils randomUtils) {
        final double generatedValue = randomUtils.randomDouble(33d, 42d);

        assertTrue(generatedValue >= 33d);
        assertTrue(generatedValue < 42d);
    }
}
