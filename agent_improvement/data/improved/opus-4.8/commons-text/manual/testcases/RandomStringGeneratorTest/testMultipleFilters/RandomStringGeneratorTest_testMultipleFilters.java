package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#filteredBy} accepts multiple predicates and
 * includes a generated character if it matches <em>any</em> of them (logical OR).
 */
public class RandomStringGeneratorTest_testMultipleFilters {

    /** Accepts only the character 'a'. */
    private static final CharacterPredicate ONLY_A = codePoint -> codePoint == 'a';

    /** Accepts only the character 'b'. */
    private static final CharacterPredicate ONLY_B = codePoint -> codePoint == 'b';

    /** Number of characters to generate; large enough to make every allowed character very likely to appear. */
    private static final int GENERATED_LENGTH = 5000;

    @Test
    void testMultipleFilters() {
        // Allow the range 'a'..'d', but keep only characters matching ONLY_A or ONLY_B.
        // Effectively, the result should contain only 'a' and 'b'.
        final String generated = RandomStringGenerator.builder()
                .withinRange('a', 'd')
                .filteredBy(ONLY_A, ONLY_B)
                .get()
                .generate(GENERATED_LENGTH);

        boolean sawA = false;
        boolean sawB = false;
        for (final char c : generated.toCharArray()) {
            if (c == 'a') {
                sawA = true;
            } else if (c == 'b') {
                sawB = true;
            } else {
                fail("Generated an unexpected character: '" + c + "'");
            }
        }

        // Both predicates should have contributed at least one character.
        assertTrue(sawA && sawB, "Expected both 'a' and 'b' to appear in the generated string");
    }
}
