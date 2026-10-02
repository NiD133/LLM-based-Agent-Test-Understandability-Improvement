package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testDecodeObjects {

    @Test
    void testDecodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        // Decode a valid Q-encoded MIME header word
        final String qEncodedInput = "=?UTF-8?Q?1+1 =3D 2?=";
        final String decodedResult = (String) qcodec.decode((Object) qEncodedInput);
        assertEquals("1+1 = 2", decodedResult, "Basic Q decoding test");

        // Decoding a null Object should return null without throwing
        final Object resultForNull = qcodec.decode((Object) null);
        assertNull(resultForNull, "Decoding a null Object should return null");

        // Decoding a non-String Object (Double) should throw DecoderException
        assertThrows(DecoderException.class,
                () -> qcodec.decode(Double.valueOf(3.0d)),
                "Trying to Q-decode a Double object should cause an exception.");
    }
}
