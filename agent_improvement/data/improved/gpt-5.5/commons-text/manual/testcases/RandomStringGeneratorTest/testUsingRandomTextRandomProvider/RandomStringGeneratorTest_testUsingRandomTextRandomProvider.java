package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testUsingRandomTextRandomProvider {

    private static final char FIXED_GENERATED_CHARACTER = 'a';
    private static final int GENERATED_LENGTH = 10;

    @Test
    void testUsingRandomTextRandomProvider() {
        final TextRandomProvider fixedCharacterProvider = n -> FIXED_GENERATED_CHARACTER;

        final String generated = RandomStringGenerator.builder()
                .usingRandom(fixedCharacterProvider)
                .get()
                .generate(GENERATED_LENGTH);

        for (final char actualCharacter : generated.toCharArray()) {
            assertEquals(FIXED_GENERATED_CHARACTER, actualCharacter);
        }
    }
}
