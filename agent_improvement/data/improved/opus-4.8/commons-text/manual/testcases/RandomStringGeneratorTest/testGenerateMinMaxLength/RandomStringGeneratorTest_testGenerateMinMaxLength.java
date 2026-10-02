package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator#generate(int, int)} honors the
 * requested minimum/maximum length bounds (measured in Unicode code points).
 */
public class RandomStringGeneratorTest_testGenerateMinMaxLength {

    /** Counts the number of Unicode code points in the given string. */
    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testGenerateMinMaxLength() {
        final int minLength = 0;
        final int maxLength = 3;

        // Default generator: any code point, length chosen within [minLength, maxLength].
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String generated = generator.generate(minLength, maxLength);

        final int actualLength = codePointLength(generated);
        assertTrue(actualLength >= minLength && actualLength <= maxLength,
                "Generated length " + actualLength + " is outside the range [" + minLength + ", " + maxLength + "]");
    }
}
