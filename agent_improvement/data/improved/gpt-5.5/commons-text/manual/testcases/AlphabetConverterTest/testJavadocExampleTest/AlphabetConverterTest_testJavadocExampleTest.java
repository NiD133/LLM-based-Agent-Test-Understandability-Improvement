package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testJavadocExampleTest {

    private static final Character[] ORIGINAL_ALPHABET = { 'a', 'b', 'c', 'd' };
    private static final Character[] ENCODING_ALPHABET = { '0', '1', 'd' };
    private static final Character[] DO_NOT_ENCODE = { 'd' };

    private AlphabetConverter createJavadocExampleConverter() {
        return AlphabetConverter.createConverterFromChars(
                ORIGINAL_ALPHABET,
                ENCODING_ALPHABET,
                DO_NOT_ENCODE);
    }

    /*
     * Test example in javadocs for consistency.
     */
    @Test
    void testJavadocExampleTest() throws UnsupportedEncodingException {
        final AlphabetConverter alphabetConverter = createJavadocExampleConverter();

        assertEquals("00", alphabetConverter.encode("a"));
        assertEquals("01", alphabetConverter.encode("b"));
        assertEquals("0d", alphabetConverter.encode("c"));
        assertEquals("d", alphabetConverter.encode("d"));
        assertEquals("00010dd", alphabetConverter.encode("abcd"));
    }
}
