package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#decode(String)} reports a clear error
 * when the encoded input ends in the middle of an encoded letter.
 */
public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    /**
     * Builds the converter from the class Javadoc example: the original alphabet
     * {a, b, c, d} is encoded with {0, 1, d}, leaving 'd' unencoded.
     *
     * <p>With these alphabets each original letter encodes to a fixed length of
     * two characters (a -> 00, b -> 01, c -> 0d), while 'd' stays as 'd'.</p>
     */
    private AlphabetConverter createJavadocExampleConverter() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void decodingStringThatEndsMidLetterThrowsUnsupportedEncoding() {
        final AlphabetConverter converter = createJavadocExampleConverter();

        // "00d01d0" decodes as: 00 (a), d (d), 01 (b), d (d), then a lone "0"
        // that needs a two-character group but only one character remains.
        final String truncatedEncoding = "00d01d0";

        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> converter.decode(truncatedEncoding));

        assertEquals(
                "Unexpected end of string while decoding " + truncatedEncoding,
                thrown.getMessage());
    }
}
