package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testWithinRange {

    @Test
    void testWithinRange() {
        final int length = 5000;
        final int minimumCodePoint = 'a';
        final int maximumCodePoint = 'z';
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange(minimumCodePoint, maximumCodePoint)
                .get();
        final String str = generator.generate(length);

        // Iterate by code point (not char index) to correctly handle supplementary characters
        int charIndex = 0;
        do {
            final int codePoint = str.codePointAt(charIndex);
            assertTrue(codePoint >= minimumCodePoint && codePoint <= maximumCodePoint);
            charIndex += Character.charCount(codePoint);
        } while (charIndex < str.length());
    }
}
