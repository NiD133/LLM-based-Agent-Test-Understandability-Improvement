package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    /**
     * Creates the example converter from the AlphabetConverter Javadoc:
     *   original:    a, b, c, d
     *   encoding:    0, 1, d
     *   doNotEncode: d
     *
     * With this setup, 'a'→"00", 'b'→"01", 'c'→"0d", 'd'→"d".
     * Every non-pass-through character requires exactly 2 encoded chars.
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original    = { 'a', 'b', 'c', 'd' };
        final Character[] encoding    = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    /**
     * Verifies that decoding a string that ends in the middle of a 2-char
     * encoded sequence throws UnsupportedEncodingException with a message
     * that identifies the truncated input.
     *
     * "00d01d0" decodes fine up to the trailing "0", which is incomplete
     * (needs a second character to form "00", "01", or "0d").
     */
    @Test
    void testUnexpectedEndWhileDecodingTest() {
        final String toDecode = "00d01d0";
        final AlphabetConverter converter = createJavadocExample();

        UnsupportedEncodingException thrown = assertThrows(
            UnsupportedEncodingException.class,
            () -> converter.decode(toDecode)
        );

        assertEquals("Unexpected end of string while decoding " + toDecode, thrown.getMessage());
    }
}
