package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator#generate(int)} produces a string
 * whose length, measured in Unicode code points, equals the requested length.
 */
public class RandomStringGeneratorTest_testSetLength {

    /**
     * Returns the number of Unicode code points in the given string. This differs
     * from {@link String#length()}, which counts {@code char} units, because a
     * supplementary character is encoded as two chars but counts as one code point.
     */
    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testSetLength() {
        final int requestedLength = 99;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();

        final String generated = generator.generate(requestedLength);

        assertEquals(requestedLength, codePointLength(generated));
    }
}
