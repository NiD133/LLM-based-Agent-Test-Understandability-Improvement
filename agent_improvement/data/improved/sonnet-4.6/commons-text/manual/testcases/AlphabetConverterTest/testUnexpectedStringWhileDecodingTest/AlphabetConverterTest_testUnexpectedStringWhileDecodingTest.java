package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedStringWhileDecodingTest {

    // Converter built from the Javadoc example: original={a,b,c,d}, encoding={0,1,d}, doNotEncode={d}
    // Encoding lengths are 2 chars (e.g. "a"->"00", "b"->"01", "c"->"0d"), except "d"->"d" (pass-through).
    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedStringWhileDecodingTest() {
        // "00" maps back to "a", but "XX" has no entry in the decoder — the converter
        // must throw when it encounters an unrecognised encoded chunk mid-string.
        final String toDecode = "00XX";
        final String expectedMessage = "Unexpected string without decoding (XX) in " + toDecode;

        final UnsupportedEncodingException exception = assertThrows(
            UnsupportedEncodingException.class,
            () -> createJavadocExample().decode(toDecode)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}
