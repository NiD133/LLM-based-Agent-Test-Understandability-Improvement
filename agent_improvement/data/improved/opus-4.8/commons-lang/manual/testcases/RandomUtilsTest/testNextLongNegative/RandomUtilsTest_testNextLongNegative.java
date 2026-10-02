package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link RandomUtils#randomLong(long, long)} rejects a negative lower bound.
 */
public class RandomUtilsTest_testNextLongNegative extends AbstractLangTest {

    /**
     * Supplies every {@link RandomUtils} flavour so the test runs once per implementation.
     */
    static Stream<RandomUtils> randomProvider() {
        return Stream.of(RandomUtils.secure(), RandomUtils.secureStrong(), RandomUtils.insecure());
    }

    /**
     * A negative {@code startInclusive} is illegal, so {@code randomLong(-1, 1)} must throw
     * {@link IllegalArgumentException} for every flavour of {@link RandomUtils}.
     */
    @ParameterizedTest
    @MethodSource("randomProvider")
    void testNextLongNegative(final RandomUtils randomUtils) {
        final long negativeStartInclusive = -1;
        final long endExclusive = 1;

        assertIllegalArgumentException(() -> randomUtils.randomLong(negativeStartInclusive, endExclusive));
    }
}
