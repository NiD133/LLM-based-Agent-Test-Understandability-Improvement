package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeInt extends AbstractLangTest {

    static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testExtremeRangeInt(final RandomUtils randomUtils) {
        final int generatedInt = randomUtils.randomInt(0, Integer.MAX_VALUE);

        assertTrue(generatedInt >= 0);
        assertTrue(generatedInt < Integer.MAX_VALUE);
    }
}
