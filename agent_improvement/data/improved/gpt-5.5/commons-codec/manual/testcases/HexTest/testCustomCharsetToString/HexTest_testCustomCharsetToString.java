package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HexTest_testCustomCharsetToString {

    @Test
    void testCustomCharsetToString() {
        final String defaultHexDescription = new Hex().toString();

        assertTrue(defaultHexDescription.contains(Hex.DEFAULT_CHARSET_NAME));
    }
}
