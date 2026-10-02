package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testNullInput {

    /**
     * Verifies that QCodec handles null input gracefully:
     * both doDecoding and doEncoding must return null when given null.
     */
    @Test
    void testNullInput() throws Exception {
        final QCodec qcodec = new QCodec();
        assertNull(qcodec.doDecoding(null));
        assertNull(qcodec.doEncoding(null));
    }
}
