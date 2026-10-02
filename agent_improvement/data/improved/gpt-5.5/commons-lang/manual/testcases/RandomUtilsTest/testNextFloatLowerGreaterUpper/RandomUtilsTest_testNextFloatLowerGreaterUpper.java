package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatLowerGreaterUpper extends AbstractLangTest {

    private static final float START_GREATER_THAN_END = 2;
    private static final float END_LESS_THAN_START = 1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomFloat(START_GREATER_THAN_END, END_LESS_THAN_START));
    }
}
