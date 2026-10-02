package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final BCodec bcodec = new BCodec();
        // Both doEncoding and doDecoding must return null when given null input
        assertNull(bcodec.doDecoding(null));
        assertNull(bcodec.doEncoding(null));
    }
}
