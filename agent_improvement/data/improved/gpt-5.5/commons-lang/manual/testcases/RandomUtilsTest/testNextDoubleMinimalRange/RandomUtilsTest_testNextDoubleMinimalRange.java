package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleMinimalRange extends AbstractLangTest {

    private static final double DOUBLE_COMPARISON_DELTA = 1e-5;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleMinimalRange(final RandomUtils randomUtils) {
        assertEquals(42.1, randomUtils.randomDouble(42.1, 42.1), DOUBLE_COMPARISON_DELTA);
    }
}
