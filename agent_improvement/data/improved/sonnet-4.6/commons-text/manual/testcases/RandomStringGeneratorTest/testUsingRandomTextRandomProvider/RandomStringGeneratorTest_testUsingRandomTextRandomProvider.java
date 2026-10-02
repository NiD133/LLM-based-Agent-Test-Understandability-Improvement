package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testUsingRandomTextRandomProvider {

    @Test
    void testUsingRandomTextRandomProvider() {
        final char expectedChar = 'a';
        // TextRandomProvider receives an upper-bound int; this one always returns 'a'.
        final TextRandomProvider fixedCharProvider = upperBound -> expectedChar;

        final String generated = RandomStringGenerator.builder()
                .usingRandom(fixedCharProvider)
                .get()
                .generate(10);

        for (final char c : generated.toCharArray()) {
            assertEquals(expectedChar, c,
                    "Every character in the 10-char string should equal the fixed provider character");
        }
    }
}
