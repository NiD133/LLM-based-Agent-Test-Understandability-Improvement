package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link URLCodec} treats a {@code null} string as a no-op for both
 * encoding and decoding, returning {@code null} rather than throwing.
 */
public class URLCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        assertNull(urlCodec.encode((String) null), "Encoding a null string should return null");
        assertNull(urlCodec.decode((String) null), "Decoding a null string should return null");
    }
}
