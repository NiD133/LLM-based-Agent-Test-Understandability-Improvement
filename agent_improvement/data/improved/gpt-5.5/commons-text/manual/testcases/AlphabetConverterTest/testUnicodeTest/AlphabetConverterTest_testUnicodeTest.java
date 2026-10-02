package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testUnicodeTest {

    private static final Integer[] ORIGINAL_CODE_POINTS = {
        32, 35395, 35397, 36302, 36291, 35203, 35201, 35215, 35219, 35268,
        97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
        1001, 1002, 1003, 1004, 1005
    };

    private static final Integer[] LOWER_CASE_ENGLISH_CODE_POINTS = {
        32, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109,
        110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122
    };

    private static final Integer[] DO_NOT_ENCODE_CODE_POINTS = {
        32, 97, 98, 99
    };

    private static final String UNICODE_TEXT = "\u8a43\u8a45 \u8dce ab \u8dc3 c \u8983";

    /**
     * Test constructor from code points.
     */
    @Test
    void testUnicodeTest() throws UnsupportedEncodingException {
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverter(
            ORIGINAL_CODE_POINTS,
            LOWER_CASE_ENGLISH_CODE_POINTS,
            DO_NOT_ENCODE_CODE_POINTS);

        assertEquals(2, alphabetConverter.getEncodedCharLength());

        final String encoded = alphabetConverter.encode(UNICODE_TEXT);
        final String decoded = alphabetConverter.decode(encoded);

        assertEquals(
            UNICODE_TEXT,
            decoded,
            () -> "Encoded '" + UNICODE_TEXT + "' into '" + encoded + "', but decoded into '" + decoded + "'");
    }
}
