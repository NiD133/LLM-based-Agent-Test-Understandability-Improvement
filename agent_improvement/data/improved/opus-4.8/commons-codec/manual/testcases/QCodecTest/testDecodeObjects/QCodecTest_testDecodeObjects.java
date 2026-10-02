package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link QCodec#decode(Object)}, which accepts an arbitrary object and:
 * <ul>
 *   <li>decodes it as a Q-encoded String when it is a String,</li>
 *   <li>returns {@code null} when the input is {@code null}, and</li>
 *   <li>throws a {@link DecoderException} for any other (non-String) type.</li>
 * </ul>
 */
public class QCodecTest_testDecodeObjects {

    @Test
    void testDecodeObjects() throws Exception {
        final QCodec qcodec = new QCodec();

        // A String is decoded from its Q-encoded form back to the original text.
        final String qEncoded = "=?UTF-8?Q?1+1 =3D 2?=";
        final String decoded = (String) qcodec.decode((Object) qEncoded);
        assertEquals("1+1 = 2", decoded, "Basic Q decoding test");

        // A null input is passed straight through as null.
        assertNull(qcodec.decode((Object) null), "Decoding a null Object should return null");

        // A non-String input cannot be decoded and must raise a DecoderException.
        assertThrows(DecoderException.class, () -> qcodec.decode(Double.valueOf(3.0d)),
                "Trying to decode a Double object should cause an exception.");
    }
}
