package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatLowerGreaterUpper extends AbstractLangTest {

    /** Lower bound intentionally exceeds upper bound to trigger validation failure. */
    private static final float INVALID_LOWER = 2;
    private static final float INVALID_UPPER = 1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomFloat throws IllegalArgumentException when lower bound is greater than upper bound")
    void testNextFloatLowerGreaterUpper(final RandomUtils ru) {
        assertIllegalArgumentException(() -> ru.randomFloat(INVALID_LOWER, INVALID_UPPER));
    }
}
