package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator#generate(int)} never produces a "lone" surrogate,
 * i.e. every UTF-16 surrogate {@code char} is always part of a valid high/low surrogate pair.
 */
public class RandomStringGeneratorTest_testNoLoneSurrogates {

    /** Number of code points to generate; large enough to make surrogate pairs very likely. */
    private static final int GENERATED_LENGTH = 5000;

    @Test
    void testNoLoneSurrogates() {
        final String generated = RandomStringGenerator.builder().get().generate(GENERATED_LENGTH);

        char previousChar = generated.charAt(0);
        for (int i = 1; i < generated.length(); i++) {
            final char currentChar = generated.charAt(i);

            // A low surrogate must always be preceded by a high surrogate.
            if (Character.isLowSurrogate(currentChar)) {
                assertTrue(Character.isHighSurrogate(previousChar));
            }
            // A high surrogate must always be followed by a low surrogate.
            if (Character.isHighSurrogate(previousChar)) {
                assertTrue(Character.isLowSurrogate(currentChar));
            }
            // A high surrogate can never be the last char, since it needs a trailing low surrogate.
            if (Character.isHighSurrogate(currentChar)) {
                assertTrue(i + 1 < generated.length());
            }

            previousChar = currentChar;
        }
    }
}
