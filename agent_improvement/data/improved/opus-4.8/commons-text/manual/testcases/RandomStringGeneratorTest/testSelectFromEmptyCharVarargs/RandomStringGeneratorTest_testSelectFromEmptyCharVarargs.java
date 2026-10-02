package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromEmptyCharVarargs {

    /**
     * Calling {@code selectFrom()} with no characters leaves the character set empty, so the
     * generator should fall back to its default behavior and pick any valid Unicode code point.
     * Every character produced must therefore lie within the legal code point range.
     */
    @Test
    void testSelectFromEmptyCharVarargs() {
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom().get();

        final String randomText = generator.generate(5);

        for (final char c : randomText.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT,
                    "Generated character '" + c + "' is outside the valid code point range");
        }
    }
}
