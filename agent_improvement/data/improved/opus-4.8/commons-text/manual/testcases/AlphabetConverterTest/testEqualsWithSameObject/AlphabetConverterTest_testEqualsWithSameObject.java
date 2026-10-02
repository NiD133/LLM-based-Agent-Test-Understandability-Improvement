package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#equals(Object)} satisfies the
 * reflexive property: a converter must always be equal to itself.
 */
public class AlphabetConverterTest_testEqualsWithSameObject {

    @Test
    void testEqualsWithSameObject() {
        // Build a minimal converter; the exact alphabet is irrelevant here,
        // we only need a valid instance to compare against itself.
        final Character[] alphabet = { 'R', 'R' };
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(alphabet, alphabet, alphabet);

        // equals() must be reflexive: an object equals itself.
        assertTrue(converter.equals(converter));
    }
}
