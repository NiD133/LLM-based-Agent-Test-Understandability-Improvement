package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BCodecTest_testNullInput {

    @Test
    void testNullInput() throws Exception {
        final BCodec bcodec = new BCodec();

        assertNull(bcodec.doDecoding(null));
        assertNull(bcodec.doEncoding(null));
    }
}
