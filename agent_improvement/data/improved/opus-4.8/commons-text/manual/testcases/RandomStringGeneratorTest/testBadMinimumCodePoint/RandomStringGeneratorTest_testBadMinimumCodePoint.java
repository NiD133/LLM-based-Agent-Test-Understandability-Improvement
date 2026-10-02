package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator.Builder#withinRange(int, int)} rejects a
 * negative minimum code point.
 */
public class RandomStringGeneratorTest_testBadMinimumCodePoint {

    /**
     * A minimum code point below zero is invalid, so configuring the builder with a
     * negative minimum must throw {@link IllegalArgumentException}.
     */
    @Test
    void testBadMinimumCodePoint() {
        final int negativeMinimumCodePoint = -1;
        final int maximumCodePoint = 1;

        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(negativeMinimumCodePoint, maximumCodePoint));
    }
}
