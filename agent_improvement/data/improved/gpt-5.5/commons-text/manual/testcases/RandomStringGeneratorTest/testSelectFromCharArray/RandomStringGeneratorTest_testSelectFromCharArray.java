package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromCharArray {

    @Test
    void testSelectFromCharArray() {
        final String allowedCharacters = "abc";
        final char[] allowedCharacterArray = allowedCharacters.toCharArray();
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(allowedCharacterArray).get();

        final String randomText = generator.generate(5);

        for (final char generatedCharacter : randomText.toCharArray()) {
            assertTrue(allowedCharacters.indexOf(generatedCharacter) != -1);
        }
    }
}
