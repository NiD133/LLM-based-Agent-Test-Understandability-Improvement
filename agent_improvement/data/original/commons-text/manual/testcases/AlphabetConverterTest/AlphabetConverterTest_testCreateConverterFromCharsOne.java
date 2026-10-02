package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsOne {

    private static final Character[] LOWER_CASE_ENGLISH = { ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    private static final Character[] ENGLISH_AND_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ' };

    private static final Character[] LOWER_CASE_ENGLISH_AND_NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ' };

    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final Character[] BINARY = { '0', '1' };

    private static final Character[] HEBREW = { '_', ' ', '\u05e7', '\u05e8', '\u05d0', '\u05d8', '\u05d5', '\u05df', '\u05dd', '\u05e4', '\u05e9', '\u05d3', '\u05d2', '\u05db', '\u05e2', '\u05d9', '\u05d7', '\u05dc', '\u05da', '\u05e3', '\u05d6', '\u05e1', '\u05d1', '\u05d4', '\u05e0', '\u05de', '\u05e6', '\u05ea', '\u05e5' };

    private static final Integer[] UNICODE = { 32, 35395, 35397, 36302, 36291, 35203, 35201, 35215, 35219, 35268, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 1001, 1002, 1003, 1004, 1005 };

    private static final Integer[] LOWER_CASE_ENGLISH_CODEPOINTS = { 32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122 };

    // space, a, b, c
    private static final Integer[] DO_NOT_ENCODE_CODEPOINTS = { 32, 97, 98, 99 };

    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    private void test(final Character[] originalChars, final Character[] encodingChars, final Character[] doNotEncodeChars, final String... strings) throws UnsupportedEncodingException {
        final AlphabetConverter ac = AlphabetConverter.createConverterFromChars(originalChars, encodingChars, doNotEncodeChars);
        final AlphabetConverter reconstructedAlphabetConverter = AlphabetConverter.createConverterFromMap(ac.getOriginalToEncoded());
        assertEquals(ac, reconstructedAlphabetConverter);
        assertEquals(ac.hashCode(), reconstructedAlphabetConverter.hashCode());
        assertEquals(ac.toString(), reconstructedAlphabetConverter.toString());
        // test null conversions
        assertNull(ac.encode(null));
        // test empty conversion
        assertEquals("", ac.encode(""));
        // test all the trial strings
        for (final String s : strings) {
            final String encoded = ac.encode(s);
            // test that only encoding chars are used
            final List<Character> originalEncodingChars = Arrays.asList(encodingChars);
            for (int i = 0; i < encoded.length(); i++) {
                assertTrue(originalEncodingChars.contains(encoded.charAt(i)));
            }
            final String decoded = ac.decode(encoded);
            // test that only the original alphabet is used after decoding
            final List<Character> originalCharsList = Arrays.asList(originalChars);
            for (int i = 0; i < decoded.length(); i++) {
                assertTrue(originalCharsList.contains(decoded.charAt(i)));
            }
            assertEquals(s, decoded, () -> "Encoded '" + s + "' into '" + encoded + "', but decoded into '" + decoded + "'");
        }
    }

    @Test
    void testCreateConverterFromCharsOne() {
        final Character[] characterArray = new Character[2];
        characterArray[0] = '5';
        characterArray[1] = characterArray[0];
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(characterArray, characterArray, characterArray);
        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
