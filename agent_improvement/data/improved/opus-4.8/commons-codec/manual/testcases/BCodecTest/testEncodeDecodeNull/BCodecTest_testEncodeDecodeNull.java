package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec} treats a {@code null} input as a no-op:
 * encoding or decoding a {@code null} string must return {@code null}
 * rather than throwing or producing an empty result.
 */
public class BCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final BCodec bcodec = new BCodec();

        assertNull(bcodec.encode((String) null), "Encoding a null string should return null");
        assertNull(bcodec.decode((String) null), "Decoding a null string should return null");
    }
}
