package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testDecodeObjects {

    private static final String ENCODED_EXPRESSION = "=?UTF-8?Q?1+1 =3D 2?=";
    private static final String DECODED_EXPRESSION = "1+1 = 2";
    private static final Double UNSUPPORTED_DECODE_INPUT = Double.valueOf(3.0d);

    @Test
    void testDecodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        final String decoded = (String) qcodec.decode((Object) ENCODED_EXPRESSION);
        assertEquals(DECODED_EXPRESSION, decoded, "Basic Q decoding test");

        final Object nullDecoded = qcodec.decode((Object) null);
        assertNull(nullDecoded, "Decoding a null Object should return null");

        assertThrows(DecoderException.class, () -> qcodec.decode(UNSUPPORTED_DECODE_INPUT),
                "Trying to url encode a Double object should cause an exception.");
    }
}
