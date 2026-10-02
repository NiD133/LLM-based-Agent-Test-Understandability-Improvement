package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testRemoveFilters {

    private static final CharacterPredicate A_FILTER = codePoint -> codePoint == 'a';

    @Test
    void testRemoveFilters() {
        // Build a generator initially restricted to only emit 'a' characters
        final RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(A_FILTER);

        // Passing no arguments removes all filters, allowing the full 'a'-'z' range
        builder.filteredBy();

        final String generated = builder.get().generate(100);

        // If the filter was removed, the 100-character string must contain at least one non-'a' character
        boolean containsNonACharacter = false;
        for (final char c : generated.toCharArray()) {
            if (c != 'a') {
                containsNonACharacter = true;
                break;
            }
        }
        assertTrue(containsNonACharacter, "Filter appears to have remained in place");
    }
}
