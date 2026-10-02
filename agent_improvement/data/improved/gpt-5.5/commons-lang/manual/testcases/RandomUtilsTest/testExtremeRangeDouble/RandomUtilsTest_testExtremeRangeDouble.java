package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeDouble extends AbstractLangTest {

    private static final double START_INCLUSIVE = 0;
    private static final double END_EXCLUSIVE = Double.MAX_VALUE;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testExtremeRangeDouble(final RandomUtils randomUtils) {
        final double result = randomUtils.randomDouble(START_INCLUSIVE, END_EXCLUSIVE);

        assertTrue(result >= START_INCLUSIVE && result <= END_EXCLUSIVE);
    }
}
