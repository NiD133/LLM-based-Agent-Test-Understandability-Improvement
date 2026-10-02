package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    // Accepts only the character 'a'
    private static final CharacterPredicate ONLY_A = codePoint -> codePoint == 'a';

    // Accepts only the character 'b'
    private static final CharacterPredicate ONLY_B = codePoint -> codePoint == 'b';

    /**
     * Verifies that calling {@code filteredBy()} a second time on the same builder replaces
     * the previously registered predicate rather than accumulating it.
     *
     * <p>The deprecated {@link RandomStringGenerator.Builder#build()} method is used
     * intentionally to exercise that code path alongside the predicate-replacement behaviour.</p>
     */
    @Test
    void testBuildDeprecated() {
        // Build a generator that starts with ONLY_A, then overrides it with ONLY_B.
        // The second filteredBy() call must replace the first, not union the two predicates.
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ONLY_A);

        String generated = builder.filteredBy(ONLY_B).build().generate(100);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c);
        }
    }
}
