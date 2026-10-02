package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testChangeOfFilter {

    // Predicates that accept only the single character they are named after
    private static final CharacterPredicate ONLY_A = codePoint -> codePoint == 'a';
    private static final CharacterPredicate ONLY_B = codePoint -> codePoint == 'b';

    /**
     * Verifies that a second call to {@code filteredBy} replaces the first filter
     * rather than combining with it: a builder initially restricted to 'a' should
     * produce only 'b' characters after the filter is overridden with ONLY_B.
     */
    @Test
    void testChangeOfFilter() {
        // Start with a filter that allows only 'a'
        final Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ONLY_A);

        // Override the filter so only 'b' is accepted; the prior filter must be gone
        final String generated = builder.filteredBy(ONLY_B).get().generate(100);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c, "Every character must be 'b' because the filter was replaced, not combined");
        }
    }
}
