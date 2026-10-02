package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        // QCodec defaults to UTF-8 charset and wraps output in RFC 1522 encoded-word format: =?UTF-8?Q?...?=
        final QCodec qcodec = new QCodec();

        // The '=' characters are unsafe in Q-encoding and must be hex-escaped as =3D.
        // '\r' and '\n' (CRLF) are control characters that must also be escaped as =0D and =0A.
        final String plainText = "= Hello there =\r\n";
        final String expectedEncoded = "=?UTF-8?Q?=3D Hello there =3D=0D=0A?=";

        final String actualEncoded = qcodec.encode(plainText);
        assertEquals(expectedEncoded, actualEncoded, "Basic Q encoding test");

        // Decoding the encoded form must recover the original plain text exactly.
        final String actualDecoded = qcodec.decode(actualEncoded);
        assertEquals(plainText, actualDecoded, "Basic Q decoding test");
    }
}
