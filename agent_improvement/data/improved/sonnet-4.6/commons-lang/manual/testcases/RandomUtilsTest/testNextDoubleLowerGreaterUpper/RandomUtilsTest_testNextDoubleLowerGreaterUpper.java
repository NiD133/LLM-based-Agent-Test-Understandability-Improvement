package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleLowerGreaterUpper extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomDouble should throw IllegalArgumentException when startInclusive > endExclusive")
    void testNextDoubleLowerGreaterUpper(final RandomUtils ru) {
        // lower bound (2) > upper bound (1), so the range is invalid
        assertIllegalArgumentException(() -> ru.randomDouble(2, 1));
    }
}
