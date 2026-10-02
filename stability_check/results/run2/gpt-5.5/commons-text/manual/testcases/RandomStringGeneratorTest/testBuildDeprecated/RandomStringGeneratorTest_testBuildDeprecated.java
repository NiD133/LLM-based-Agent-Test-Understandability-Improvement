package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    @Test
    void testBuildDeprecated() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(A_FILTER);

        final String generated = builder.filteredBy(B_FILTER).build().generate(100);

        for (final char character : generated.toCharArray()) {
            assertEquals('b', character);
        }
    }
}
