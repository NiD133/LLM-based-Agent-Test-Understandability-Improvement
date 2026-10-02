package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testStrictDecoding {

    @Test
    void testStrictDecoding() {
        final String oddLengthEncodedHex = "aabbccdde";
        final Base16 strictLowerCaseBase16 = new Base16(true, CodecPolicy.STRICT);

        assertEquals(CodecPolicy.STRICT, strictLowerCaseBase16.getCodecPolicy());
        assertThrows(IllegalArgumentException.class,
                () -> strictLowerCaseBase16.decode(StringUtils.getBytesUtf8(oddLengthEncodedHex)));
    }
}
