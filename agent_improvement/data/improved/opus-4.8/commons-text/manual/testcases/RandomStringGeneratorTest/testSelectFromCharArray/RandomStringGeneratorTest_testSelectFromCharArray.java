package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#selectFrom(char...)} restricts the
 * generated string to the supplied pool of characters.
 */
public class RandomStringGeneratorTest_testSelectFromCharArray {

    @Test
    void testSelectFromCharArray() {
        // Restrict the generator to draw only from the characters 'a', 'b' and 'c'.
        final String allowedCharacters = "abc";
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .selectFrom(allowedCharacters.toCharArray())
                .get();

        final String randomText = generator.generate(5);

        // Every character produced must come from the allowed pool.
        for (final char generatedChar : randomText.toCharArray()) {
            assertTrue(allowedCharacters.indexOf(generatedChar) != -1,
                    () -> "Generated character '" + generatedChar + "' is not in the allowed pool \"" + allowedCharacters + "\"");
        }
    }
}
