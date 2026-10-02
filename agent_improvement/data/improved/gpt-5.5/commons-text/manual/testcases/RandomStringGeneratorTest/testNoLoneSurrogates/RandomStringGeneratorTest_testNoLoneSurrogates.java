package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testNoLoneSurrogates {

    private static final int GENERATED_CODE_POINT_COUNT = 5000;

    @Test
    void testNoLoneSurrogates() {
        final String generated = RandomStringGenerator.builder().get().generate(GENERATED_CODE_POINT_COUNT);

        char previousChar = generated.charAt(0);
        for (int index = 1; index < generated.length(); index++) {
            final char currentChar = generated.charAt(index);

            assertSurrogatesArePaired(generated, previousChar, currentChar, index);

            previousChar = currentChar;
        }
    }

    private static void assertSurrogatesArePaired(final String generated, final char previousChar, final char currentChar, final int currentIndex) {
        if (Character.isLowSurrogate(currentChar)) {
            assertTrue(Character.isHighSurrogate(previousChar));
        }
        if (Character.isHighSurrogate(previousChar)) {
            assertTrue(Character.isLowSurrogate(currentChar));
        }
        if (Character.isHighSurrogate(currentChar)) {
            assertTrue(currentIndex + 1 < generated.length());
        }
    }
}
