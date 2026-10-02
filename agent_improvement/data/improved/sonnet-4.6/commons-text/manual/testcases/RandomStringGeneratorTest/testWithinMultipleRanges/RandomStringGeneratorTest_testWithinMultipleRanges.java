package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testWithinMultipleRanges {

    @Test
    void testWithinMultipleRanges() {
        final int length = 5000;
        final char[][] ranges = { { 'a', 'z' }, { '0', '9' } };

        // withinRange() and withinRange(null) reset the character set before applying the real ranges
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange()
                .withinRange((char[][]) null)
                .withinRange(ranges)
                .get();

        final String generated = generator.generate(length);

        int minCodePoint = 0;
        int maxCodePoint = 0;
        for (final char[] range : ranges) {
            minCodePoint = Math.min(minCodePoint, range[0]);
            maxCodePoint = Math.max(maxCodePoint, range[1]);
        }

        assertAllCodePointsWithinBounds(generated, minCodePoint, maxCodePoint);
    }

    private static void assertAllCodePointsWithinBounds(final String str, final int min, final int max) {
        int index = 0;
        while (index < str.length()) {
            final int codePoint = str.codePointAt(index);
            assertTrue(codePoint >= min && codePoint <= max,
                    () -> String.format("Code point %d ('%c') is outside the allowed range [%d, %d]",
                            codePoint, (char) codePoint, min, max));
            index += Character.charCount(codePoint);
        }
    }
}
