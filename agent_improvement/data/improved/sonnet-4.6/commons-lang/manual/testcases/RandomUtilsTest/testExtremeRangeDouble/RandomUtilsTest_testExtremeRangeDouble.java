package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomDouble(double, double)} handles the widest possible
 * double range [0, Double.MAX_VALUE] without throwing or returning an out-of-bounds value.
 */
public class RandomUtilsTest_testExtremeRangeDouble extends AbstractLangTest {

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("randomProvider")
    @DisplayName("randomDouble(0, Double.MAX_VALUE) returns a value within [0, Double.MAX_VALUE]")
    void testExtremeRangeDouble(final RandomUtils ru) {
        final double startInclusive = 0;
        final double endInclusive = Double.MAX_VALUE;

        final double result = ru.randomDouble(startInclusive, endInclusive);

        assertTrue(result >= startInclusive && result <= endInclusive,
            "Result should be within [0, Double.MAX_VALUE] but was: " + result);
    }
}
