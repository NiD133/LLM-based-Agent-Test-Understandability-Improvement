package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link QCodec} performs a round trip: encoding a plain string
 * into its Q (RFC 1522) form and decoding it back yields the original string.
 */
public class QCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();

        // A string mixing the unsafe characters '=', SPACE, CR and LF, which Q
        // encoding must escape as =3D, kept-space, =0D and =0A respectively.
        final String plainText = "= Hello there =\r\n";
        final String expectedEncoded = "=?UTF-8?Q?=3D Hello there =3D=0D=0A?=";

        // Encoding wraps the text in the "=?charset?Q?...?=" envelope and escapes
        // the unsafe characters.
        assertEquals(expectedEncoded, qcodec.encode(plainText), "Basic Q encoding test");

        // Decoding the encoded form must reproduce the original text exactly.
        assertEquals(plainText, qcodec.decode(expectedEncoded), "Basic Q decoding test");
    }
}
