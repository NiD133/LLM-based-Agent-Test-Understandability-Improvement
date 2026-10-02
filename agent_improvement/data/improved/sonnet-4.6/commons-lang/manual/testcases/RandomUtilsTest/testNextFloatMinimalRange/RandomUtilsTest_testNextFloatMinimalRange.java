package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class RandomUtilsTest_testNextFloatMinimalRange extends AbstractLangTest {

    /** Tolerance for float comparisons. */
    private static final double DELTA = 1e-5;

    /** The single bound used when start == end, making the range zero-width. */
    private static final float ZERO_WIDTH_BOUND = 42.1f;

    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * When start and end are equal, {@code randomFloat} must return that exact value
     * because the range has zero width and there is only one possible outcome.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextFloatMinimalRange(final RandomUtils ru) {
        assertEquals(ZERO_WIDTH_BOUND, ru.randomFloat(ZERO_WIDTH_BOUND, ZERO_WIDTH_BOUND), DELTA);
    }
}
