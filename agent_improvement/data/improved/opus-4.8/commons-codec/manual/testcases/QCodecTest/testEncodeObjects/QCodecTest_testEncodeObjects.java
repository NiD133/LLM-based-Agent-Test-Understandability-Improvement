package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link QCodec#encode(Object)}, which dispatches on the runtime type of its argument.
 */
public class QCodecTest_testEncodeObjects {

    @Test
    void testEncodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        // A String argument is Q-encoded and wrapped in the RFC 1522 "=?charset?Q?...?=" envelope.
        final String encodedString = (String) qcodec.encode((Object) "1+1 = 2");
        assertEquals("=?UTF-8?Q?1+1 =3D 2?=", encodedString, "Basic Q encoding test");

        // A null argument is passed through unchanged.
        assertNull(qcodec.encode((Object) null), "Encoding a null Object should return null");

        // A non-String argument cannot be encoded and is rejected.
        assertThrows(EncoderException.class, () -> qcodec.encode(Double.valueOf(3.0d)),
                "Trying to url encode a Double object should cause an exception.");
    }
}
