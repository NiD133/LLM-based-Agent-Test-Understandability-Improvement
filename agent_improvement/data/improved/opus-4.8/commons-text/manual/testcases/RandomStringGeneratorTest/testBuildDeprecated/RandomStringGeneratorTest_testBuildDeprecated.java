package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link RandomStringGenerator.Builder#filteredBy(CharacterPredicate...)}
 * keeps only the predicates from its most recent invocation: a later call replaces,
 * rather than adds to, any previously configured filters.
 */
public class RandomStringGeneratorTest_testBuildDeprecated {

    /** Accepts only the letter 'a'. */
    private static final CharacterPredicate ACCEPTS_ONLY_A = codePoint -> codePoint == 'a';

    /** Accepts only the letter 'b'. */
    private static final CharacterPredicate ACCEPTS_ONLY_B = codePoint -> codePoint == 'b';

    private static final int GENERATED_LENGTH = 100;

    @Test
    void testBuildDeprecated() {
        // Configure an 'a'-only filter, then override it with a 'b'-only filter.
        // Because the second filteredBy call replaces the first, only 'b' should be produced.
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ACCEPTS_ONLY_A)
                .filteredBy(ACCEPTS_ONLY_B)
                .build();

        final String generated = generator.generate(GENERATED_LENGTH);

        for (final char actualChar : generated.toCharArray()) {
            assertEquals('b', actualChar);
        }
    }
}
