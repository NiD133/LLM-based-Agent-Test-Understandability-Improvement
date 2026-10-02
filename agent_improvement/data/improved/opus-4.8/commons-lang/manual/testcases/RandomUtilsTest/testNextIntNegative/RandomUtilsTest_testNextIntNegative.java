package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomInt(int, int)} rejects a negative lower
 * bound, regardless of which underlying random source is used.
 */
public class RandomUtilsTest_testNextIntNegative extends AbstractLangTest {

    private static final int NEGATIVE_START_INCLUSIVE = -1;
    private static final int END_EXCLUSIVE = 1;

    /**
     * Supplies every {@link RandomUtils} variant so the test runs once per source.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextIntNegative(final RandomUtils randomUtils) {
        // A negative start value is invalid and must raise IllegalArgumentException.
        assertIllegalArgumentException(() -> randomUtils.randomInt(NEGATIVE_START_INCLUSIVE, END_EXCLUSIVE));
    }
}
