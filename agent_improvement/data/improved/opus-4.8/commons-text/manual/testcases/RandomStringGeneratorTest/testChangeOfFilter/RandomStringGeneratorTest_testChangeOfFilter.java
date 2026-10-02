package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that calling {@link RandomStringGenerator.Builder#filteredBy} a second
 * time replaces the previously configured filter rather than combining with it.
 */
public class RandomStringGeneratorTest_testChangeOfFilter {

    /** Accepts only the character 'a'. */
    private static final CharacterPredicate ACCEPT_ONLY_A = codePoint -> codePoint == 'a';

    /** Accepts only the character 'b'. */
    private static final CharacterPredicate ACCEPT_ONLY_B = codePoint -> codePoint == 'b';

    /** Number of characters to generate for the assertion sample. */
    private static final int GENERATED_LENGTH = 100;

    @Test
    void replacingTheFilterUsesOnlyTheLatestPredicate() {
        // Configure a generator over 'a'-'z', first with the 'a' filter, then
        // override it with the 'b' filter. Only the last filter should take effect.
        final RandomStringGenerator generator = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ACCEPT_ONLY_A)
                .filteredBy(ACCEPT_ONLY_B)
                .get();

        final String generated = generator.generate(GENERATED_LENGTH);

        // Because the 'b' filter replaced the 'a' filter, every character must be 'b'.
        for (final char actual : generated.toCharArray()) {
            assertEquals('b', actual);
        }
    }
}
