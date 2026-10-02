package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QCodecTest_testBasicEncodeDecode {

    @Test
    void testBasicEncodeDecode() throws Exception {
        final QCodec qcodec = new QCodec();
        final String plainText = "= Hello there =\r\n";
        final String expectedEncodedText = "=?UTF-8?Q?=3D Hello there =3D=0D=0A?=";

        final String encodedText = qcodec.encode(plainText);

        assertEquals(expectedEncodedText, encodedText, "Basic Q encoding test");
        assertEquals(plainText, qcodec.decode(encodedText), "Basic Q decoding test");
    }
}
