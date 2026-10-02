package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEqualsWithSameObject {

    @Test
    void testEqualsWithSameObject() {
        // An alphabet where both original and encoding are {'R', 'R'} (duplicates collapse to one element)
        final Character[] singleCharAlphabet = { 'R', 'R' };
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // equals() must be reflexive: an object must equal itself
        assertTrue(alphabetConverter.equals(alphabetConverter));
    }
}
