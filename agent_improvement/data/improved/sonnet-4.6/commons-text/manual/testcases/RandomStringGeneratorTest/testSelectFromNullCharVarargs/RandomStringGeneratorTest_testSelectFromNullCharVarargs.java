package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromNullCharVarargs {

    private static int codePointLength(final String s) {
        return s.codePointCount(0, s.length());
    }

    // Verifies that every char in text falls within the full Unicode code point range,
    // i.e. the generator applied no character restriction.
    private static void assertAllCharsAreAnyValidCodePoint(final String text) {
        for (final char c : text.toCharArray()) {
            assertTrue(c >= Character.MIN_CODE_POINT && c <= Character.MAX_CODE_POINT);
        }
    }

    @Test
    void testSelectFromNullCharVarargs() {
        final int length = 5;

        // Passing null to selectFrom() on a fresh builder means no character restriction:
        // the generator should produce a valid-length string drawn from the full Unicode range.
        RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(null).get();
        String randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        assertAllCharsAreAnyValidCodePoint(randomText);

        // Selecting only 'a' restricts all generated characters to 'a'.
        final Builder builder = RandomStringGenerator.builder().selectFrom('a');
        generator = builder.get();
        randomText = generator.generate(length);
        for (final char c : randomText.toCharArray()) {
            assertEquals('a', c);
        }

        // Calling selectFrom(null) on a builder that already had a character restriction
        // resets that restriction; the generator should again produce unrestricted characters.
        generator = builder.selectFrom(null).get();
        randomText = generator.generate(length);
        assertEquals(length, codePointLength(randomText));
        assertAllCharsAreAnyValidCodePoint(randomText);
    }
}
