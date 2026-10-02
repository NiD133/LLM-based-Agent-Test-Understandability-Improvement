package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromCharVarargs {

    @Test
    void testSelectFromCharVarargs() {
        final String allowedCharacters = "abc";
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom('a', 'b', 'c').get();

        final String randomText = generator.generate(5);

        for (final char generatedCharacter : randomText.toCharArray()) {
            assertTrue(allowedCharacters.indexOf(generatedCharacter) != -1);
        }
    }
}
