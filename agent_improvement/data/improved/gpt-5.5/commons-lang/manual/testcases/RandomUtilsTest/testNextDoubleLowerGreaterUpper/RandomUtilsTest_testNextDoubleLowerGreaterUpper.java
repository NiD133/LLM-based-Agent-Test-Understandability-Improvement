package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleLowerGreaterUpper extends AbstractLangTest {

    private static final double LOWER_BOUND_GREATER_THAN_UPPER_BOUND = 2;
    private static final double UPPER_BOUND_LESS_THAN_LOWER_BOUND = 1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextDoubleLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(
                () -> randomUtils.randomDouble(LOWER_BOUND_GREATER_THAN_UPPER_BOUND, UPPER_BOUND_LESS_THAN_LOWER_BOUND));
    }
}
