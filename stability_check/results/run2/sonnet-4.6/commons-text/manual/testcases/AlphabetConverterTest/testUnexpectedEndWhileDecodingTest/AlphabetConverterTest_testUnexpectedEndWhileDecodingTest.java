package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnexpectedEndWhileDecodingTest {

    // Javadoc example: a,b,c,d encoded using 0,1,d with 'd' left as-is
    private AlphabetConverter createJavadocExample() {
        final Character[] original = {'a', 'b', 'c', 'd'};
        final Character[] encoding = {'0', '1', 'd'};
        final Character[] doNotEncode = {'d'};
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    @Test
    void testUnexpectedEndWhileDecodingTest() {
        // "00d01d0" ends with a single "0" which is not a complete 2-char encoded token
        final String toDecode = "00d01d0";
        final AlphabetConverter converter = createJavadocExample();

        final UnsupportedEncodingException exception = assertThrows(
                UnsupportedEncodingException.class,
                () -> converter.decode(toDecode));

        assertEquals("Unexpected end of string while decoding " + toDecode, exception.getMessage());
    }
}
