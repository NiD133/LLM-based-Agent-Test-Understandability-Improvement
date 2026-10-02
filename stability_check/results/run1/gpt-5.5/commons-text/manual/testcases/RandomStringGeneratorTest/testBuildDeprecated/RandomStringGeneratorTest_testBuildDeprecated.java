package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    private static final CharacterPredicate ACCEPTS_ONLY_A = codePoint -> codePoint == 'a';

    private static final CharacterPredicate ACCEPTS_ONLY_B = codePoint -> codePoint == 'b';

    @Test
    void testBuildDeprecated() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder().withinRange('a', 'z').filteredBy(ACCEPTS_ONLY_A);

        final String generatedString = builder.filteredBy(ACCEPTS_ONLY_B).build().generate(100);

        assertContainsOnlyB(generatedString);
    }

    private static void assertContainsOnlyB(final String generatedString) {
        for (final char c : generatedString.toCharArray()) {
            assertEquals('b', c);
        }
    }
}
