package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    /**
     * Builds the converter from the Javadoc example:
     *   original  = {a, b, c, d}
     *   encoding  = {0, 1, d}
     *   doNotEncode = {d}
     * This means 'a' -> "00", 'b' -> "01", 'c' -> "0d", 'd' -> "d".
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original    = { 'a', 'b', 'c', 'd' };
        final Character[] encoding    = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // "00d01d0" = "a" + "c" + "b" + incomplete token — the trailing "0"
        // needs one more character to form a valid two-char encoded group.
        final String incompletlyEncoded = "00d01d0";

        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> createJavadocExample().decode(incompletlyEncoded));

        assertEquals(
                "Unexpected end of string while decoding " + incompletlyEncoded,
                exception.getMessage());
    }
}
