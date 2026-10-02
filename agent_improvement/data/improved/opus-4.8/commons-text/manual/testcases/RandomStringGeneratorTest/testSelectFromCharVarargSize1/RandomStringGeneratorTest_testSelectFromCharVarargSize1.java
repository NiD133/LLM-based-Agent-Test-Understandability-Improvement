package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that when a generator is restricted to a single character via
 * {@link RandomStringGenerator.Builder#selectFrom(char...)}, every character it
 * produces is that one allowed character.
 */
public class RandomStringGeneratorTest_testSelectFromCharVarargSize1 {

    /** The only character the generator under test is allowed to produce. */
    private static final char ONLY_ALLOWED_CHAR = 'a';

    /** Number of code points requested from the generator. */
    private static final int GENERATED_LENGTH = 5;

    @Test
    void selectFromSingleCharProducesOnlyThatChar() {
        // Restrict the generator to a single allowed character.
        final RandomStringGenerator generator =
                RandomStringGenerator.builder().selectFrom(ONLY_ALLOWED_CHAR).get();

        final String generated = generator.generate(GENERATED_LENGTH);

        // Since only one character is allowed, every position must hold it.
        for (final char actual : generated.toCharArray()) {
            assertEquals(ONLY_ALLOWED_CHAR, actual);
        }
    }
}
