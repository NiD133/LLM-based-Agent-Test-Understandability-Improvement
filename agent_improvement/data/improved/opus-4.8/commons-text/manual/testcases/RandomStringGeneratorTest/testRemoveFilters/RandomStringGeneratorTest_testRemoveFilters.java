package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link RandomStringGenerator.Builder#filteredBy(CharacterPredicate...)},
 * when called with no arguments, clears any previously configured filter.
 */
public class RandomStringGeneratorTest_testRemoveFilters {

    /** A predicate that only accepts the letter 'a'. */
    private static final CharacterPredicate ONLY_LETTER_A = codePoint -> codePoint == 'a';

    @Test
    void removingFilterAllowsCharactersOtherThanA() {
        // Configure a generator restricted to a-z and further filtered down to only 'a'.
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ONLY_LETTER_A);

        // Remove the filter by calling filteredBy() with no predicates.
        builder.filteredBy();

        // With the filter removed, the generated string should contain letters other than 'a'.
        final String generated = builder.get().generate(100);
        for (final char c : generated.toCharArray()) {
            if (c != 'a') {
                // Found a non-'a' character, so the filter was successfully removed.
                return;
            }
        }
        fail("Filter appears to have remained in place");
    }
}
