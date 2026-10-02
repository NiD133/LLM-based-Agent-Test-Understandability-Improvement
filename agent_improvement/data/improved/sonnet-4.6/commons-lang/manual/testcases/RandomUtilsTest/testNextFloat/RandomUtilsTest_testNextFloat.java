package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloat extends AbstractLangTest {

    private static final float START_INCLUSIVE = 33f;
    private static final float END_EXCLUSIVE = 42f;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomFloat returns a value within [startInclusive, endExclusive)")
    void testNextFloat(final RandomUtils ru) {
        final float result = ru.randomFloat(START_INCLUSIVE, END_EXCLUSIVE);
        assertTrue(result >= START_INCLUSIVE, "Result should be >= startInclusive");
        assertTrue(result < END_EXCLUSIVE, "Result should be < endExclusive");
    }
}
