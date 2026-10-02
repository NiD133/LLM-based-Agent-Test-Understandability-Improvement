package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class QCodecTest_testUnsafeEncodeDecode {

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();

        // '?', '_', '=', CR, LF are all unsafe Q-encoding characters and must be percent-encoded
        final String plain = "?_=\r\n";
        final String encoded = qcodec.encode(plain);

        assertEquals("=?UTF-8?Q?=3F=5F=3D=0D=0A?=", encoded, "Unsafe chars Q encoding test");
        assertEquals(plain, qcodec.decode(encoded), "Unsafe chars Q decoding test");
    }
}
