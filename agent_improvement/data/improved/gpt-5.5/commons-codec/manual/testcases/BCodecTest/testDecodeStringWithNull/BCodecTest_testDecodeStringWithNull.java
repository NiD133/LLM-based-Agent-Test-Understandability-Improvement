package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class BCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws Exception {
        final BCodec bcodec = new BCodec();
        final String test = null;
        final String result = bcodec.decode(test);

        assertNull(result, "Result should be null");
    }
}
