package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    private static final Character[] ORIGINAL_ALPHABET = { 'a', 'b', 'c', 'd' };
    private static final Character[] ENCODING_ALPHABET = { '0', '1', 'd' };
    private static final Character[] DO_NOT_ENCODE = { 'd' };

    private AlphabetConverter createJavadocExample() {
        return AlphabetConverter.createConverterFromChars(
                ORIGINAL_ALPHABET,
                ENCODING_ALPHABET,
                DO_NOT_ENCODE);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        final String incompleteEncodedText = "00d01d0";

        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(incompleteEncodedText));

        assertEquals(
                "Unexpected end of string while decoding " + incompleteEncodedText,
                exception.getMessage());
    }
}
