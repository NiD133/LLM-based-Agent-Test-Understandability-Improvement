package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloat extends AbstractLangTest {

    private static final float LOWER_BOUND_INCLUSIVE = 33f;
    private static final float UPPER_BOUND_EXCLUSIVE = 42f;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloat(final RandomUtils randomUtils) {
        final float randomValue = randomUtils.randomFloat(LOWER_BOUND_INCLUSIVE, UPPER_BOUND_EXCLUSIVE);

        assertTrue(randomValue >= LOWER_BOUND_INCLUSIVE);
        assertTrue(randomValue < UPPER_BOUND_EXCLUSIVE);
    }
}
