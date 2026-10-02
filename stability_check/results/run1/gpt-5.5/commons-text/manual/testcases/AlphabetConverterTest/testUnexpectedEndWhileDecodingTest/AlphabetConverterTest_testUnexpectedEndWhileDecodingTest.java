package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    private static final Character[] JAVADOC_ORIGINAL_ALPHABET = { 'a', 'b', 'c', 'd' };
    private static final Character[] JAVADOC_ENCODING_ALPHABET = { '0', '1', 'd' };
    private static final Character[] JAVADOC_DO_NOT_ENCODE = { 'd' };

    private static final String TRUNCATED_ENCODED_TEXT = "00d01d0";
    private static final String UNEXPECTED_END_MESSAGE =
            "Unexpected end of string while decoding " + TRUNCATED_ENCODED_TEXT;

    private AlphabetConverter createJavadocExample() {
        return AlphabetConverter.createConverterFromChars(
                JAVADOC_ORIGINAL_ALPHABET,
                JAVADOC_ENCODING_ALPHABET,
                JAVADOC_DO_NOT_ENCODE);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(TRUNCATED_ENCODED_TEXT));

        assertEquals(UNEXPECTED_END_MESSAGE, thrown.getMessage());
    }
}
