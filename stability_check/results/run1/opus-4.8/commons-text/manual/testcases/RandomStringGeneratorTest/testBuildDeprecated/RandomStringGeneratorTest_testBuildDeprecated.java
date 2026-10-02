package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#filteredBy} keeps only the
 * predicates from the most recent call, and that the deprecated
 * {@link RandomStringGenerator.Builder#build()} method still works.
 */
public class RandomStringGeneratorTest_testBuildDeprecated {

    /** Accepts only the letter 'a'. */
    private static final CharacterPredicate ACCEPT_ONLY_A = codePoint -> codePoint == 'a';

    /** Accepts only the letter 'b'. */
    private static final CharacterPredicate ACCEPT_ONLY_B = codePoint -> codePoint == 'b';

    private static final int GENERATED_LENGTH = 100;

    @Test
    void testBuildDeprecated() {
        // The second filteredBy(...) call replaces the first, so only 'b' remains allowed.
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ACCEPT_ONLY_A)
                .filteredBy(ACCEPT_ONLY_B)
                .build();

        final String generated = generator.generate(GENERATED_LENGTH);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c, "Every generated character should be 'b'");
        }
    }
}
