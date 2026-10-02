package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec#decodeUrl(byte[])} treats a {@code null}
 * input as a no-op and returns {@code null}.
 */
public class URLCodecTest_testDecodeWithNullArray {

    @Test
    void testDecodeWithNullArray() throws Exception {
        final byte[] nullInput = null;

        final byte[] decoded = URLCodec.decodeUrl(nullInput);

        assertNull(decoded, "Decoding a null byte array should return null");
    }
}
