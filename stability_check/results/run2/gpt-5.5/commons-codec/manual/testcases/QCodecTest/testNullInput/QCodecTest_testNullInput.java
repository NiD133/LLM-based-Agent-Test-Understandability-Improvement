package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final QCodec qCodec = new QCodec();

        assertNull(qCodec.doDecoding(null));
        assertNull(qCodec.doEncoding(null));
    }
}
