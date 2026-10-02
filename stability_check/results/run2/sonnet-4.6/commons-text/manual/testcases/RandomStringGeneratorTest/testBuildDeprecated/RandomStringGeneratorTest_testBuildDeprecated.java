package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.text.RandomStringGenerator.Builder;
import org.junit.jupiter.api.Test;

public class RandomStringGeneratorTest_testBuildDeprecated {

    // Predicate that accepts only the character 'a'
    private static final CharacterPredicate ONLY_A = codePoint -> codePoint == 'a';

    // Predicate that accepts only the character 'b'
    private static final CharacterPredicate ONLY_B = codePoint -> codePoint == 'b';

    @Test
    void testBuildDeprecated() {
        // Calling filteredBy() a second time replaces the previously set filter entirely.
        // Although ONLY_A is set first, the subsequent call with ONLY_B overwrites it,
        // so the generated string must consist exclusively of 'b' characters.
        final Builder builder = RandomStringGenerator.builder()
                .withinRange('a', 'z')
                .filteredBy(ONLY_A);  // initial filter — will be replaced

        final String generated = builder
                .filteredBy(ONLY_B)  // replaces ONLY_A
                .build()
                .generate(100);

        for (final char c : generated.toCharArray()) {
            assertEquals('b', c, "Every character must be 'b' because the second filteredBy() replaced the first");
        }
    }
}
