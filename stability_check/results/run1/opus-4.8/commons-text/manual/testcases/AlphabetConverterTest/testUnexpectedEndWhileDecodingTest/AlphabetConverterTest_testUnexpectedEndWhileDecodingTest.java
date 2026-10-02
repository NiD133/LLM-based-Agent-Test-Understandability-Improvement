package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#decode(String)} reports an
 * "unexpected end of string" error when the input ends in the middle of an
 * encoded group.
 */
public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    /**
     * Builds the converter used in the class Javadoc:
     * <ul>
     *   <li>original alphabet: a, b, c, d</li>
     *   <li>encoding alphabet: 0, 1, d</li>
     *   <li>do-not-encode:     d</li>
     * </ul>
     * With this setup every original letter (except {@code d}) is encoded as a
     * fixed two-character group, so the decoder expects the input length to be a
     * multiple of that group length.
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // "00d01d0" ends with a lone "0", which cannot form a complete encoded group.
        final String incompleteEncoding = "00d01d0";
        final String expectedMessage = "Unexpected end of string while decoding " + incompleteEncoding;

        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(incompleteEncoding));

        assertEquals(expectedMessage, thrown.getMessage());
    }
}
