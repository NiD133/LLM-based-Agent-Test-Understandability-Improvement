package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeWithNullArray {

    @Test
    void testDecodeWithNullArray() throws DecoderException {
        // URLCodec.decodeUrl(null) must return null per its contract
        assertNull(URLCodec.decodeUrl(null), "Result should be null");
    }
}
