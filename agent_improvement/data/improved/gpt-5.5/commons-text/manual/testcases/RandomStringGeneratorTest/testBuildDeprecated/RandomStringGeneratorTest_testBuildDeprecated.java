package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    private static final CharacterPredicate ACCEPTS_A = codePoint -> codePoint == 'a';
    private static final CharacterPredicate ACCEPTS_B = codePoint -> codePoint == 'b';
    private static final int GENERATED_LENGTH = 100;

    @Test
    void testBuildDeprecated() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ACCEPTS_A);

        final String generated = builder.filteredBy(ACCEPTS_B)
                .build()
                .generate(GENERATED_LENGTH);

        for (final char character : generated.toCharArray()) {
            assertEquals('b', character);
        }
    }
}
