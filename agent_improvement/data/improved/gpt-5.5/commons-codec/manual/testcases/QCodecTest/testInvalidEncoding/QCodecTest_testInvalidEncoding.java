package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

public class QCodecTest_testInvalidEncoding {

    private static final String INVALID_CHARSET_NAME = "NONSENSE";

    @Test
    void testInvalidEncoding() {
        assertThrows(UnsupportedCharsetException.class, () -> new QCodec(INVALID_CHARSET_NAME));
    }
}
