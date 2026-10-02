package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntMinimalRange extends AbstractLangTest {

    private static final int SINGLE_VALUE_RANGE_BOUNDARY = 42;

    static Stream<RandomUtils> randomUtilsInstances() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsInstances")
    void testNextIntMinimalRange(final RandomUtils randomUtils) {
        assertEquals(SINGLE_VALUE_RANGE_BOUNDARY,
                randomUtils.randomInt(SINGLE_VALUE_RANGE_BOUNDARY, SINGLE_VALUE_RANGE_BOUNDARY));
    }
}
