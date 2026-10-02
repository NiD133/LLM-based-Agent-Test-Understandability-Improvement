package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEqualsWithSameObject {

    @Test
    void testEqualsWithSameObject() {
        final Character[] singleLetterAlphabet = new Character[2];
        final char repeatedLetter = 'R';
        singleLetterAlphabet[0] = repeatedLetter;
        singleLetterAlphabet[1] = repeatedLetter;

        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                singleLetterAlphabet,
                singleLetterAlphabet,
                singleLetterAlphabet);

        assertTrue(alphabetConverter.equals(alphabetConverter));
    }
}
