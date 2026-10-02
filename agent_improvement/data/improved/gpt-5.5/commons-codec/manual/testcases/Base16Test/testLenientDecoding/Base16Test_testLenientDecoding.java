package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testLenientDecoding {

    private static final String ODD_LENGTH_LOWER_CASE_HEX = "aabbccdde";
    private static final byte[] COMPLETE_HEX_PAIR_BYTES = {
            (byte) 0xaa,
            (byte) 0xbb,
            (byte) 0xcc,
            (byte) 0xdd
    };

    @Test
    void testLenientDecoding() {
        final Base16 base16 = new Base16(true, CodecPolicy.LENIENT);

        assertEquals(CodecPolicy.LENIENT, base16.getCodecPolicy());

        final byte[] decoded = base16.decode(StringUtils.getBytesUtf8(ODD_LENGTH_LOWER_CASE_HEX));

        assertArrayEquals(COMPLETE_HEX_PAIR_BYTES, decoded);
    }
}
