package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator.Builder#withinRange(int, int)} rejects a
 * maximum code point greater than {@link Character#MAX_CODE_POINT}.
 */
public class RandomStringGeneratorTest_testBadMaximumCodePoint {

    @Test
    void withinRangeRejectsMaximumCodePointAboveUnicodeLimit() {
        final int tooLargeMaximumCodePoint = Character.MAX_CODE_POINT + 1;

        assertThrowsExactly(IllegalArgumentException.class,
                () -> RandomStringGenerator.builder().withinRange(0, tooLargeMaximumCodePoint));
    }
}
