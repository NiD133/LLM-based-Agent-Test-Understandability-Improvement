package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextDoubleMinimalRange extends AbstractLangTest {

    /** Tolerance for floating-point equality comparisons. */
    private static final double DELTA = 1e-5;

    /**
     * Arbitrary value used as both the start and end of the degenerate (zero-width) range.
     * When start == end, {@code randomDouble} must return exactly that value.
     */
    private static final double DEGENERATE_RANGE_VALUE = 42.1;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * Verifies that {@code randomDouble(x, x)} returns {@code x} when the range has zero width
     * (i.e. start and end are equal), regardless of the random-number source used.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    @DisplayName("randomDouble returns the boundary value when start equals end (zero-width range)")
    void testNextDoubleMinimalRange(final RandomUtils randomUtils) {
        assertEquals(DEGENERATE_RANGE_VALUE,
                randomUtils.randomDouble(DEGENERATE_RANGE_VALUE, DEGENERATE_RANGE_VALUE),
                DELTA);
    }
}
