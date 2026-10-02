package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link BCodec}'s low-level byte routines are null-safe:
 * decoding or encoding a {@code null} byte array must return {@code null}
 * rather than throwing.
 */
public class BCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final BCodec bcodec = new BCodec();

        assertNull(bcodec.doDecoding(null), "Decoding a null byte array should return null");
        assertNull(bcodec.doEncoding(null), "Encoding a null byte array should return null");
    }
}
