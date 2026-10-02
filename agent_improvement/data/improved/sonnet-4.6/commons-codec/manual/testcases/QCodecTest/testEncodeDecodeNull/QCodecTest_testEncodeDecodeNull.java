package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeDecodeNull {

    @Test
    void testEncodeDecodeNull() throws Exception {
        final QCodec qcodec = new QCodec();
        assertNull(qcodec.encode((String) null), "Null string Q encoding test");
        assertNull(qcodec.decode((String) null), "Null string Q decoding test");
    }
}
