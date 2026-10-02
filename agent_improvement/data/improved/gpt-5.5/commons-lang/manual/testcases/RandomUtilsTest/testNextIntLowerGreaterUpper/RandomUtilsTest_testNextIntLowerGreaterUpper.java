package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntLowerGreaterUpper extends AbstractLangTest {

    private static final int LOWER_BOUND_GREATER_THAN_UPPER_BOUND = 2;
    private static final int UPPER_BOUND_LESS_THAN_LOWER_BOUND = 1;

    static Stream<RandomUtils> randomUtilsProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomUtilsProvider")
    void testNextIntLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomInt(
                LOWER_BOUND_GREATER_THAN_UPPER_BOUND,
                UPPER_BOUND_LESS_THAN_LOWER_BOUND));
    }
}
