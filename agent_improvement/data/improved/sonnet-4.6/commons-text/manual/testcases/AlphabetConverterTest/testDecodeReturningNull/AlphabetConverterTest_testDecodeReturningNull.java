package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testDecodeReturningNull {

    @Test
    void testDecodeReturningNull() throws UnsupportedEncodingException {
        // A converter built from an empty map has no character mappings
        final Map<Integer, String> emptyMap = new HashMap<>();
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromMap(emptyMap);

        // decode(null) should return null without throwing an exception
        alphabetConverter.decode(null);

        // With no mappings, encoded char length defaults to 1
        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
