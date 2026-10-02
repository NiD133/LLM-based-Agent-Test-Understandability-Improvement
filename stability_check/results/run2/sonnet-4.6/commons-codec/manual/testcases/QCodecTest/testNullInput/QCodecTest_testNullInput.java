package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final QCodec qcodec = new QCodec();
        assertNull(qcodec.doDecoding(null), "doDecoding(null) should return null");
        assertNull(qcodec.doEncoding(null), "doEncoding(null) should return null");
    }
}
