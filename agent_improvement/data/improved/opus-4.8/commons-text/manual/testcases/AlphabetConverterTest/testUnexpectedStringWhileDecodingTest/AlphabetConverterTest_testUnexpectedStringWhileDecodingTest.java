package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#decode(String)} reports a helpful error
 * when the input contains an encoded group that the converter does not recognize.
 */
public class AlphabetConverterTest_testUnexpectedStringWhileDecodingTest {

    /**
     * Builds the converter from the class Javadoc example:
     * <ul>
     *   <li>original alphabet: a, b, c, d</li>
     *   <li>encoding alphabet: 0, 1, d</li>
     *   <li>do-not-encode:     d</li>
     * </ul>
     * This produces fixed-length (2-char) encodings such as a -&gt; "00", b -&gt; "01", c -&gt; "0d".
     */
    private AlphabetConverter createJavadocExample() {
        final Character[] original = { 'a', 'b', 'c', 'd' };
        final Character[] encoding = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void decodingUnrecognizedGroupThrowsWithDescriptiveMessage() {
        final AlphabetConverter converter = createJavadocExample();

        // "00" decodes to 'a', but "XX" is not a known encoding group.
        final String inputWithUnknownGroup = "00XX";

        final UnsupportedEncodingException thrown = assertThrows(
                UnsupportedEncodingException.class,
                () -> converter.decode(inputWithUnknownGroup));

        assertEquals(
                "Unexpected string without decoding (XX) in " + inputWithUnknownGroup,
                thrown.getMessage());
    }
}
