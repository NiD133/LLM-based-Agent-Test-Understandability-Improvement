package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testMultipleFilters {

    private static final CharacterPredicate ACCEPT_ONLY_A = codePoint -> codePoint == 'a';
    private static final CharacterPredicate ACCEPT_ONLY_B = codePoint -> codePoint == 'b';

    @Test
    void testMultipleFilters() {
        final String generated = RandomStringGenerator.builder()
                .withinRange('a', 'd')
                .filteredBy(ACCEPT_ONLY_A, ACCEPT_ONLY_B)
                .get()
                .generate(5000);

        boolean sawA = false;
        boolean sawB = false;
        for (final char c : generated.toCharArray()) {
            if (c == 'a') {
                sawA = true;
            } else if (c == 'b') {
                sawB = true;
            } else {
                fail("Unexpected character '" + c + "': only 'a' and 'b' should pass the filters");
            }
        }

        assertTrue(sawA, "Expected 'a' to appear in the generated string");
        assertTrue(sawB, "Expected 'b' to appear in the generated string");
    }
}
