package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromCharVarargSize1 {

    @Test
    void testSelectFromCharVarargSize1() {
        // When selectFrom is given a single character, every character in the generated string must be that character.
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom('a').get();
        final String generatedString = generator.generate(5);
        for (final char c : generatedString.toCharArray()) {
            assertEquals('a', c, "Expected only 'a' but found '" + c + "' in generated string: " + generatedString);
        }
    }
}
