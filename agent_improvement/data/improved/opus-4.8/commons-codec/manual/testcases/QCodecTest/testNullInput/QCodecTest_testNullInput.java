package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec}'s low-level byte routines return {@code null}
 * when given {@code null} input, rather than throwing or returning an empty array.
 */
public class QCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final QCodec qcodec = new QCodec();

        assertNull(qcodec.doDecoding(null), "Decoding null bytes should return null");
        assertNull(qcodec.doEncoding(null), "Encoding null bytes should return null");
    }
}
