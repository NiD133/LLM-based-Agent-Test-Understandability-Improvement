package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    private static final Character[] JAVADOC_ORIGINAL_ALPHABET = { 'a', 'b', 'c', 'd' };
    private static final Character[] JAVADOC_ENCODING_ALPHABET = { '0', '1', 'd' };
    private static final Character[] JAVADOC_DO_NOT_ENCODE = { 'd' };

    private AlphabetConverter createJavadocExample() {
        return AlphabetConverter.createConverterFromChars(
                JAVADOC_ORIGINAL_ALPHABET,
                JAVADOC_ENCODING_ALPHABET,
                JAVADOC_DO_NOT_ENCODE);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        final String toDecode = "00d01d0";

        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(toDecode));

        assertEquals("Unexpected end of string while decoding " + toDecode, exception.getMessage());
    }
}
