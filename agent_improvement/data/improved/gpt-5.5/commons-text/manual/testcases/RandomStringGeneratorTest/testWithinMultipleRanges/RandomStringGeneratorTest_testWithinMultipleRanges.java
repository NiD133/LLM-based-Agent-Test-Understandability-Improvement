package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testWithinMultipleRanges {

    private static final int GENERATED_CODE_POINT_COUNT = 5000;
    private static final char[][] ALPHANUMERIC_RANGES = { { 'a', 'z' }, { '0', '9' } };

    @Test
    void testWithinMultipleRanges() {
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange()
                .withinRange((char[][]) null)
                .withinRange(ALPHANUMERIC_RANGES)
                .get();

        final String generatedString = generator.generate(GENERATED_CODE_POINT_COUNT);
        final int minimumCodePoint = minimumCodePoint(ALPHANUMERIC_RANGES);
        final int maximumCodePoint = maximumCodePoint(ALPHANUMERIC_RANGES);

        assertEveryCodePointWithinRange(generatedString, minimumCodePoint, maximumCodePoint);
    }

    private static void assertEveryCodePointWithinRange(final String generatedString, final int minimumCodePoint, final int maximumCodePoint) {
        int index = 0;
        do {
            final int codePoint = generatedString.codePointAt(index);
            assertTrue(codePoint >= minimumCodePoint && codePoint <= maximumCodePoint);
            index += Character.charCount(codePoint);
        } while (index < generatedString.length());
    }

    private static int maximumCodePoint(final char[][] ranges) {
        int maximumCodePoint = 0;
        for (final char[] range : ranges) {
            maximumCodePoint = Math.max(maximumCodePoint, range[1]);
        }
        return maximumCodePoint;
    }

    private static int minimumCodePoint(final char[][] ranges) {
        int minimumCodePoint = 0;
        for (final char[] range : ranges) {
            minimumCodePoint = Math.min(minimumCodePoint, range[0]);
        }
        return minimumCodePoint;
    }
}
