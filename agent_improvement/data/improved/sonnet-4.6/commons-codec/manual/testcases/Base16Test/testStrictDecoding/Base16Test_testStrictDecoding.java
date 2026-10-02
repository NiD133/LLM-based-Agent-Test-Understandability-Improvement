package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testStrictDecoding {

    @Test
    void testStrictDecoding() {
        // "aabbccdde" has an odd number of hex characters: the trailing 'e' forms only half
        // a byte pair, which is invalid under strict decoding rules.
        final String encodedWithTrailingHalfByte = "aabbccdde";

        final Base16 strictDecoder = new Base16(true, CodecPolicy.STRICT);

        assertEquals(CodecPolicy.STRICT, strictDecoder.getCodecPolicy());
        assertThrows(IllegalArgumentException.class,
                () -> strictDecoder.decode(StringUtils.getBytesUtf8(encodedWithTrailingHalfByte)));
    }
}
