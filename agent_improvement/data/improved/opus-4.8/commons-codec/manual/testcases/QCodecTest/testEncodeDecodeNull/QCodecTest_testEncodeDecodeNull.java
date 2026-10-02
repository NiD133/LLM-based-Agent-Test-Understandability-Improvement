package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec} treats a {@code null} input as a no-op:
 * both encoding and decoding a {@code null} string return {@code null}
 * rather than throwing or producing a value.
 */
public class QCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final QCodec qcodec = new QCodec();

        assertNull(qcodec.encode((String) null), "Encoding a null string should return null");
        assertNull(qcodec.decode((String) null), "Decoding a null string should return null");
    }
}
