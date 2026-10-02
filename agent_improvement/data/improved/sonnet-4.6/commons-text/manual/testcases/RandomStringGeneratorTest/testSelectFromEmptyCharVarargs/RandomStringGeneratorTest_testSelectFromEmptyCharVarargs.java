package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromEmptyCharVarargs {

    @Test
    void testSelectFromEmptyCharVarargs() {
        // Calling selectFrom() with no arguments means no character set restriction,
        // so the generator falls back to producing any valid Unicode code point.
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom().get();
        final String randomText = generator.generate(5);
        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }
}
