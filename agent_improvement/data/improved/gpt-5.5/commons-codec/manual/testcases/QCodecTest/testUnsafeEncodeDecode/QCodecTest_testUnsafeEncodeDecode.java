package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QCodecTest_testUnsafeEncodeDecode {

    private static final String UNSAFE_CHARACTERS = "?_=\r\n";
    private static final String Q_ENCODED_UNSAFE_CHARACTERS = "=?UTF-8?Q?=3F=5F=3D=0D=0A?=";

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();

        final String encoded = qcodec.encode(UNSAFE_CHARACTERS);
        assertEquals(Q_ENCODED_UNSAFE_CHARACTERS, encoded, "Unsafe chars Q encoding test");
        assertEquals(UNSAFE_CHARACTERS, qcodec.decode(encoded), "Unsafe chars Q decoding test");
    }
}
