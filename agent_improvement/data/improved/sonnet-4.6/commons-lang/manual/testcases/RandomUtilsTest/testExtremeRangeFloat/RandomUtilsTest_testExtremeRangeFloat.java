package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testExtremeRangeFloat extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomFloat(0, Float.MAX_VALUE) returns a value within [0, Float.MAX_VALUE]")
    void testExtremeRangeFloat(final RandomUtils ru) {
        final float result = ru.randomFloat(0, Float.MAX_VALUE);
        assertTrue(result >= 0f && result <= Float.MAX_VALUE,
                "Expected result in [0, Float.MAX_VALUE] but was: " + result);
    }
}
