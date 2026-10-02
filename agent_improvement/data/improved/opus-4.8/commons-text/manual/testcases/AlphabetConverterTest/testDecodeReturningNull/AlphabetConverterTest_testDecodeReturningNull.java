package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testDecodeReturningNull {

    /**
     * A converter built from an empty mapping decodes {@code null} to {@code null}
     * without altering its (default) encoded-character length of 1.
     */
    @Test
    void testDecodeReturningNull() throws UnsupportedEncodingException {
        final Map<Integer, String> emptyMapping = new HashMap<>();
        final AlphabetConverter converter = AlphabetConverter.createConverterFromMap(emptyMapping);

        converter.decode(null);

        assertEquals(1, converter.getEncodedCharLength());
    }
}
