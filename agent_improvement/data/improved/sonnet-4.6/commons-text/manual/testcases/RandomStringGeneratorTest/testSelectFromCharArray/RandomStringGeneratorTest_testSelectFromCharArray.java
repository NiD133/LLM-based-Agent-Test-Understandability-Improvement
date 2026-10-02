package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testSelectFromCharArray {

    @Test
    void testSelectFromCharArray() {
        final String allowedChars = "abc";
        final char[] charArray = allowedChars.toCharArray();
        final RandomStringGenerator generator = RandomStringGenerator.builder().selectFrom(charArray).get();

        final String randomText = generator.generate(5);

        for (final char c : randomText.toCharArray()) {
            assertTrue(allowedChars.indexOf(c) != -1,
                "Generated character '" + c + "' is not in the allowed set: " + allowedChars);
        }
    }
}
