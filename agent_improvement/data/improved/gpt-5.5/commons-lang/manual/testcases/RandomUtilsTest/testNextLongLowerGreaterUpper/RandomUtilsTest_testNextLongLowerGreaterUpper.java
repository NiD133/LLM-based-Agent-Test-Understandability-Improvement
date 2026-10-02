package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextLongLowerGreaterUpper extends AbstractLangTest {

    private static final long LOWER_BOUND_GREATER_THAN_UPPER_BOUND = 2L;
    private static final long UPPER_BOUND_LESS_THAN_LOWER_BOUND = 1L;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongLowerGreaterUpper(final RandomUtils randomUtils) {
        assertIllegalArgumentException(() -> randomUtils.randomLong(LOWER_BOUND_GREATER_THAN_UPPER_BOUND, UPPER_BOUND_LESS_THAN_LOWER_BOUND));
    }
}
