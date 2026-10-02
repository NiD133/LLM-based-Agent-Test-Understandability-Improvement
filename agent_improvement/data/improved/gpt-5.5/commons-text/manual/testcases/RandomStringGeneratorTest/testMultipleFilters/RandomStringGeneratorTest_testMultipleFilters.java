package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testMultipleFilters {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    private static final CharacterPredicate B_FILTER = codePoint -> codePoint == 'b';

    @Test
    void testMultipleFilters() {
        final String generatedString = RandomStringGenerator.builder()
                .withinRange('a', 'd')
                .filteredBy(A_FILTER, B_FILTER)
                .get()
                .generate(5000);

        boolean foundA = false;
        boolean foundB = false;
        for (final char character : generatedString.toCharArray()) {
            if (character == 'a') {
                foundA = true;
            } else if (character == 'b') {
                foundB = true;
            } else {
                fail("Invalid character");
            }
        }
        assertTrue(foundA && foundB);
    }
}
