package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link URLCodec#encode(byte[])} handles a {@code null} input.
 */
public class URLCodecTest_testEncodeNull {

    @Test
    void encodeNullByteArrayReturnsNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final byte[] encoded = urlCodec.encode((byte[]) null);

        assertNull(encoded, "Encoding a null byte array should return null");
    }
}
