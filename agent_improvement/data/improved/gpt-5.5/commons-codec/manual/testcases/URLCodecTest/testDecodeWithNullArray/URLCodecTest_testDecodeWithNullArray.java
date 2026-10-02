package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class URLCodecTest_testDecodeWithNullArray {

    @Test
    void testDecodeWithNullArray() throws DecoderException {
        final byte[] encodedBytes = null;

        final byte[] decodedBytes = URLCodec.decodeUrl(encodedBytes);

        assertNull(decodedBytes, "Decoding a null byte array should return null");
    }
}
