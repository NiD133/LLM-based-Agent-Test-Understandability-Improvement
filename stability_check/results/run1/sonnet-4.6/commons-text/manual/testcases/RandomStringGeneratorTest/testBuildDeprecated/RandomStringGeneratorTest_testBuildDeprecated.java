package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    // Accepts only the character 'a'
    private static final CharacterPredicate ONLY_A = codePoint -> codePoint == 'a';

    // Accepts only the character 'b'
    private static final CharacterPredicate ONLY_B = codePoint -> codePoint == 'b';

    @Test
    void testBuildDeprecated() {
        // Set up a builder restricted to lowercase letters, initially filtered to 'a' only.
        final Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ONLY_A);

        // A second call to filteredBy() replaces the previous filter, so only 'b' passes.
        // The deprecated build() method is used intentionally here to verify it still works.
        final String generated = builder.filteredBy(ONLY_B).build().generate(100);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c,
                "Expected 'b' because the second filteredBy(ONLY_B) replaced the first filter");
        }
    }
}
