package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testDecodeReturningNull {

    @Test
    void testDecodeReturningNull() throws UnsupportedEncodingException {
        final Map<Integer, String> originalToEncoded = new HashMap<>();
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromMap(originalToEncoded);

        alphabetConverter.decode(null);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
