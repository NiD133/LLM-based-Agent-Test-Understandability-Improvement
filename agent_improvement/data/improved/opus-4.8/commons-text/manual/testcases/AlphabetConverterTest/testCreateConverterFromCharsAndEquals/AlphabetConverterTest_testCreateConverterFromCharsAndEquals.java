package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link AlphabetConverter#equals(Object)} when the converter is built
 * from char arrays and then compared against a non-converter object.
 */
public class AlphabetConverterTest_testCreateConverterFromCharsAndEquals {

    @Test
    void testCreateConverterFromCharsAndEquals() {
        // Build a minimal converter whose original, encoding and "do not encode"
        // alphabets all consist of the single character '+' (the duplicate entry
        // in the array is collapsed internally).
        final char plusSign = '+';
        final Character[] plusAlphabet = { plusSign, plusSign };

        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(plusAlphabet, plusAlphabet, plusAlphabet);

        // A converter is never equal to an object of a different type, such as a
        // bare Character, so equals(...) must return false.
        assertFalse(converter.equals(plusSign));
    }
}
