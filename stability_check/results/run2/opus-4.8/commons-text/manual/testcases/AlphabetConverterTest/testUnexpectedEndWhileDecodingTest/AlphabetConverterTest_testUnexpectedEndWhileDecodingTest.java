package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#decode(String)} reports a helpful error
 * when the input ends in the middle of an encoded letter.
 */
public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    /**
     * Builds the converter from the Javadoc sample usage of {@link AlphabetConverter}.
     * <p>
     * Original alphabet {@code a, b, c, d} is encoded with {@code 0, 1, d}, leaving
     * {@code d} unencoded. This yields fixed-length (2-char) encodings for the
     * encoded letters: {@code a -> 00}, {@code b -> 01}, {@code c -> 0d}, and the
     * single-char {@code d -> d}.
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // "00d01d0" decodes as "00"(a) + "d"(d) + "01"(b) + "d"(d) + trailing "0",
        // where the lone final "0" cannot form a complete 2-char encoded letter.
        final String toDecode = "00d01d0";

        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(toDecode));

        assertEquals("Unexpected end of string while decoding " + toDecode, thrown.getMessage());
    }
}
