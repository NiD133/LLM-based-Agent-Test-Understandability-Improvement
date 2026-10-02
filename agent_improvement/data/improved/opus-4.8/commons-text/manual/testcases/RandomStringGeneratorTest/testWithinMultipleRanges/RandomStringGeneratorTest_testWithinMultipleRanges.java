package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator.Builder#withinRange(char[][])} confines the
 * generated characters to the union of the supplied {min, max} character ranges.
 */
public class RandomStringGeneratorTest_testWithinMultipleRanges {

    /** Number of code points to generate; large enough to exercise every allowed range. */
    private static final int GENERATED_LENGTH = 5000;

    /** The allowed character ranges, each expressed as a {minimum, maximum} pair. */
    private static final char[][] ALLOWED_RANGES = { { 'a', 'z' }, { '0', '9' } };

    @Test
    void testWithinMultipleRanges() {
        // Earlier withinRange() calls (no-arg and null) are no-ops that must not
        // affect the final configuration; only ALLOWED_RANGES should take effect.
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange()
                .withinRange((char[][]) null)
                .withinRange(ALLOWED_RANGES)
                .get();

        final String generated = generator.generate(GENERATED_LENGTH);

        // Derive the overall bounds that every generated code point must satisfy.
        // The minimum starts at 0 (matching the original test), so it stays 0 here.
        int minimumCodePoint = 0;
        int maximumCodePoint = 0;
        for (final char[] range : ALLOWED_RANGES) {
            minimumCodePoint = Math.min(minimumCodePoint, range[0]);
            maximumCodePoint = Math.max(maximumCodePoint, range[1]);
        }

        // Every code point in the result must fall within [minimumCodePoint, maximumCodePoint].
        int index = 0;
        do {
            final int codePoint = generated.codePointAt(index);
            assertTrue(codePoint >= minimumCodePoint && codePoint <= maximumCodePoint);
            index += Character.charCount(codePoint);
        } while (index < generated.length());
    }
}
