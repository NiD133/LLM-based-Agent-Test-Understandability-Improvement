package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntUnaryOperator;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testUsingRandomIntUnaryOperator {

    @Test
    void testUsingRandomIntUnaryOperator() {
        final char expectedCharacter = 'a';
        final IntUnaryOperator alwaysReturnsExpectedCharacter = bound -> expectedCharacter;

        final String generatedString = RandomStringGenerator.builder()
                .usingRandom(alwaysReturnsExpectedCharacter)
                .get()
                .generate(10);

        for (final char generatedCharacter : generatedString.toCharArray()) {
            assertEquals(expectedCharacter, generatedCharacter);
        }
    }
}
