package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator} delegates character selection to a custom
 * {@link TextRandomProvider} supplied via {@link RandomStringGenerator.Builder#usingRandom(TextRandomProvider)}.
 */
public class RandomStringGeneratorTest_testUsingRandomTextRandomProvider {

    /**
     * When the custom random provider always returns the index of the same character, every
     * character in the generated string must be that character.
     */
    @Test
    void testUsingRandomTextRandomProvider() {
        final char expectedChar = 'a';

        // A provider that always returns the same value, so generation is fully deterministic.
        final TextRandomProvider constantProvider = bound -> expectedChar;

        final String generated = RandomStringGenerator.builder()
                .usingRandom(constantProvider)
                .get()
                .generate(10);

        for (final char actualChar : generated.toCharArray()) {
            assertEquals(expectedChar, actualChar);
        }
    }
}
