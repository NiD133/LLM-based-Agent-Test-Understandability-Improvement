package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSetLength {

    // Counts Unicode code points rather than Java char units: supplementary
    // characters span two chars but count as a single code point.
    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    @Test
    void testSetLength() {
        final int length = 99;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String generated = generator.generate(length);
        assertEquals(length, codePointLength(generated));
    }
}
