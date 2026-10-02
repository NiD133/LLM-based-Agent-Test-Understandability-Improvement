package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeStringWithNull {

    @Test
    void testEncodeStringWithNull() throws Exception {
        final QCodec qcodec = new QCodec();
        final String source = null;

        final String encoded = qcodec.encode(source, "charset");

        assertNull(encoded, "Result should be null");
    }
}
