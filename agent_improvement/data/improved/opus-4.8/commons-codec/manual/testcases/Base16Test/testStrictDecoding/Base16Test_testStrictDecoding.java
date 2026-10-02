package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

/**
 * Tests that a {@link Base16} codec configured with the {@link CodecPolicy#STRICT}
 * decoding policy rejects input that ends with a "dangling" character.
 */
public class Base16Test_testStrictDecoding {

    @Test
    void testStrictDecoding() {
        // "aabbccdd" decodes to four bytes; the trailing "e" is a lone hex digit
        // that cannot form a complete byte (a byte needs two hex characters).
        final String encodedWithDanglingChar = "aabbccdde";

        // lowerCase = true, decoding policy = STRICT
        final Base16 strictCodec = new Base16(true, CodecPolicy.STRICT);

        assertEquals(CodecPolicy.STRICT, strictCodec.getCodecPolicy());

        // Under STRICT decoding, the incomplete trailing character must be rejected.
        assertThrows(IllegalArgumentException.class,
                () -> strictCodec.decode(StringUtils.getBytesUtf8(encodedWithDanglingChar)));
    }
}
