package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec}'s low-level byte routines treat a {@code null}
 * input as a no-op, returning {@code null} rather than throwing.
 */
public class QCodecTest_testNullInput {

    @Test
    void nullInputReturnsNullForDecodingAndEncoding() throws Exception {
        final QCodec qcodec = new QCodec();

        assertNull(qcodec.doDecoding(null), "decoding null should yield null");
        assertNull(qcodec.doEncoding(null), "encoding null should yield null");
    }
}
