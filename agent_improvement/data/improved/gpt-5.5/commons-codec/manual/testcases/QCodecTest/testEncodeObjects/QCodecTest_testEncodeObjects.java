package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

public class QCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        final String plain = "1+1 = 2";
        final String encoded = (String) qcodec.encode((Object) plain);
        assertEquals("=?UTF-8?Q?1+1 =3D 2?=", encoded, "Basic Q encoding test");

        final Object result = qcodec.encode((Object) null);
        assertNull(result, "Encoding a null Object should return null");

        assertThrows(EncoderException.class, () -> qcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}
