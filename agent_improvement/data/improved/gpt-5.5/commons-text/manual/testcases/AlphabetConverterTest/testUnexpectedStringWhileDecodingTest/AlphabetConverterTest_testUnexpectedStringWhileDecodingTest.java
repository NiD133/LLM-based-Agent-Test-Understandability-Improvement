package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedStringWhileDecodingTest {

    private static final String INVALID_ENCODED_TEXT = "00XX";

    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };

        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedStringWhileDecodingTest() {
        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(INVALID_ENCODED_TEXT));

        assertEquals(
                "Unexpected string without decoding (XX) in " + INVALID_ENCODED_TEXT,
                exception.getMessage());
    }
}
