package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link BCodec} handles a {@code null} input when decoding a string.
 */
public class BCodecTest_testDecodeStringWithNull {

    /**
     * Decoding a {@code null} string should return {@code null} rather than throwing.
     */
    @Test
    void testDecodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();

        final String decoded = bcodec.decode((String) null);

        assertNull(decoded, "Decoding a null string should yield null");
    }
}
