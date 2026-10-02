package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testChangeOfFilter {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';
    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    @Test
    void testChangeOfFilter() {
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder().withinRange('a', 'z').filteredBy(A_FILTER);
        final String generatedString = builder.filteredBy(B_FILTER).get().generate(100);

        for (final char generatedCharacter : generatedString.toCharArray()) {
            assertEquals('b', generatedCharacter);
        }
    }
}
