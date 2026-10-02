package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

public class HexTest_testCustomCharsetBadName {

    // A charset name that is not recognised by the JVM
    private static final String BAD_ENCODING_NAME = "UNKNOWN";

    @Test
    void testCustomCharsetBadName() {
        assertThrows(UnsupportedCharsetException.class, () -> new Hex(BAD_ENCODING_NAME));
    }
}
