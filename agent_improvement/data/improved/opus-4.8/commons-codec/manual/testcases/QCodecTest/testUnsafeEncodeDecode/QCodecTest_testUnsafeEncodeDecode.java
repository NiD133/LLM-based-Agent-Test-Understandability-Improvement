package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec} round-trips a string made up entirely of
 * characters that are "unsafe" in the Q encoding (the '?', '_' and '='
 * delimiters plus a CR/LF pair), so every character must be escaped.
 */
public class QCodecTest_testUnsafeEncodeDecode {

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();

        // Every character here is unsafe and must be hex-escaped as "=XX".
        final String plain = "?_=\r\n";
        final String expectedEncoded = "=?UTF-8?Q?=3F=5F=3D=0D=0A?=";

        final String encoded = qcodec.encode(plain);

        assertEquals(expectedEncoded, encoded, "Unsafe chars Q encoding test");
        assertEquals(plain, qcodec.decode(encoded), "Unsafe chars Q decoding test");
    }
}
