package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSetLength {

    private static int codePointLength(final String value) {
        return value.codePointCount(0, value.length());
    }

    @Test
    void testSetLength() {
        final int requestedLength = 99;
        final RandomStringGenerator generator = RandomStringGenerator.builder().get();
        final String generated = generator.generate(requestedLength);

        assertEquals(requestedLength, codePointLength(generated));
    }
}
