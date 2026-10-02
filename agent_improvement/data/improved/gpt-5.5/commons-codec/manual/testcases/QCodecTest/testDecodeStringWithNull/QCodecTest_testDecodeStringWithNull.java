package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testDecodeStringWithNull {

    @Test
    void testDecodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();
        final String test = null;
        final String result = qcodec.decode(test);

        assertNull(result, "Result should be null");
    }
}
