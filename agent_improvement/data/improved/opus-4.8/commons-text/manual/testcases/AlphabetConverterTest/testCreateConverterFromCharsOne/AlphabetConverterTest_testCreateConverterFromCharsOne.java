package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsOne {

    /**
     * When the original, encoding and "do not encode" alphabets all consist of
     * the very same single character (duplicates are ignored), the converter
     * only needs one character to encode each original character. The encoded
     * char length should therefore be 1.
     */
    @Test
    void testCreateConverterFromCharsOne() {
        // All three alphabets are the same single repeated character: { '5', '5' }.
        final Character singleChar = '5';
        final Character[] sameAlphabet = { singleChar, singleChar };

        final AlphabetConverter alphabetConverter =
                AlphabetConverter.createConverterFromChars(sameAlphabet, sameAlphabet, sameAlphabet);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
