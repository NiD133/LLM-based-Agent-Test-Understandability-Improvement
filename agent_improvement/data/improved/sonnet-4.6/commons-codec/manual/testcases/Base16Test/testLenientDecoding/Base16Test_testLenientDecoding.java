package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testLenientDecoding {

    @Test
    void testLenientDecoding() {
        // "aabbccdd" is 4 complete hex pairs; trailing "e" is an incomplete pair (only 1 nibble)
        // LENIENT policy drops incomplete trailing pairs rather than throwing
        final String encodedWithTrailingHalfByte = "aabbccdde";

        final Base16 b16 = new Base16(true, CodecPolicy.LENIENT);

        assertEquals(CodecPolicy.LENIENT, b16.getCodecPolicy());

        final byte[] decoded = b16.decode(StringUtils.getBytesUtf8(encodedWithTrailingHalfByte));

        // Only the 4 complete hex pairs are decoded; the trailing "e" is silently ignored
        final byte[] expectedBytes = { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd };
        assertArrayEquals(expectedBytes, decoded);
    }
}
