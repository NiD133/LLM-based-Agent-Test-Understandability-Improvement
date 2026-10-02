package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextIntLowerGreaterUpper extends AbstractLangTest {

    /** An invalid range where the lower bound exceeds the upper bound. */
    private static final int INVALID_LOWER = 2;
    private static final int INVALID_UPPER = 1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomInt} throws {@link IllegalArgumentException} when
     * {@code startInclusive} is greater than {@code endExclusive}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomInt throws IllegalArgumentException when lower > upper")
    void testNextIntLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomInt(INVALID_LOWER, INVALID_UPPER));
    }
}
