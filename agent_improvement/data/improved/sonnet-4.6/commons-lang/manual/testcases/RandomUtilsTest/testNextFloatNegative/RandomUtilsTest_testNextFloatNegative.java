package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("RandomUtils.randomFloat() — negative start value")
public class RandomUtilsTest_testNextFloatNegative extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("randomProvider")
    @DisplayName("should throw IllegalArgumentException when startInclusive is negative")
    void testNextFloatNegative(final RandomUtils ru) {
        // randomFloat requires startInclusive >= 0; passing -1 must be rejected
        assertIllegalArgumentException(() -> ru.randomFloat(-1, 1));
    }
}
