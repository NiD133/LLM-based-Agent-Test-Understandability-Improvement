package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testNoPrivateCharacters {

    /**
     * The first code point of the Private Use Area in the Basic Multilingual Plane (BMP).
     * The range {@code 0xE000}..{@code 0xF8FF} is reserved for private use characters.
     */
    private static final int FIRST_PRIVATE_USE_BMP_CODE_POINT = 0xE000;

    /** The largest code point that still lies within the Basic Multilingual Plane. */
    private static final int LAST_BMP_CODE_POINT = Character.MIN_SUPPLEMENTARY_CODE_POINT - 1;

    /** Number of code points to generate; large enough to reliably exercise the private-use range. */
    private static final int GENERATED_LENGTH = 5000;

    @Test
    void testNoPrivateCharacters() {
        // Request a string from a slice of the Basic Multilingual Plane that is
        // largely occupied by private-use characters. The generator must never
        // emit any of them.
        final String generated = RandomStringGenerator.builder()
                .withinRange(FIRST_PRIVATE_USE_BMP_CODE_POINT, LAST_BMP_CODE_POINT)
                .get()
                .generate(GENERATED_LENGTH);

        // Walk the string code point by code point (a single code point may span
        // two chars) and confirm none of them are private-use characters.
        int charIndex = 0;
        do {
            final int codePoint = generated.codePointAt(charIndex);
            assertFalse(Character.getType(codePoint) == Character.PRIVATE_USE);
            charIndex += Character.charCount(codePoint);
        } while (charIndex < generated.length());
    }
}
