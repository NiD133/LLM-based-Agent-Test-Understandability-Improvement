package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that repeated {@link RandomStringGenerator.Builder#filteredBy} calls
 * replace the previously configured predicate rather than accumulating them.
 */
public class RandomStringGeneratorTest_testBuildDeprecated {

    /** Accepts only the character 'a'. */
    private static final CharacterPredicate ACCEPT_ONLY_A = codePoint -> codePoint == 'a';

    /** Accepts only the character 'b'. */
    private static final CharacterPredicate ACCEPT_ONLY_B = codePoint -> codePoint == 'b';

    private static final int GENERATED_LENGTH = 100;

    @Test
    void filteredByKeepsOnlyTheLastPredicate() {
        // The first filteredBy(ACCEPT_ONLY_A) is overridden by the second
        // filteredBy(ACCEPT_ONLY_B), so only 'b' should ever be produced.
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ACCEPT_ONLY_A)
                .filteredBy(ACCEPT_ONLY_B)
                .build();

        final String generated = generator.generate(GENERATED_LENGTH);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c);
        }
    }
}
