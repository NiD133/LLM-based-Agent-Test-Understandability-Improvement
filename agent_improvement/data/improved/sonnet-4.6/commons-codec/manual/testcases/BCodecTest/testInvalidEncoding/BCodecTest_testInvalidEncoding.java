package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.Test;

public class BCodecTest_testInvalidEncoding {

    @Test
    void testInvalidEncoding() {
        assertThrows(UnsupportedCharsetException.class, () -> new BCodec("NONSENSE"));
    }
}
