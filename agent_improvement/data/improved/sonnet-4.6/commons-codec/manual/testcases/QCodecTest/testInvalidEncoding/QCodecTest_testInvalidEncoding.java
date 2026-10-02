package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.UnsupportedCharsetException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class QCodecTest_testInvalidEncoding {

    @Test
    @DisplayName("Constructing QCodec with an unrecognised charset name throws UnsupportedCharsetException")
    void testInvalidEncoding() {
        assertThrows(UnsupportedCharsetException.class, () -> new QCodec("NONSENSE"));
    }
}
