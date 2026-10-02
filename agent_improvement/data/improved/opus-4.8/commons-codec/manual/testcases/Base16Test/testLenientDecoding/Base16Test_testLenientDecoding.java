package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.CodecPolicy;
import org.junit.jupiter.api.Test;

public class Base16Test_testLenientDecoding {

    /**
     * Verifies that a {@link Base16} codec configured with {@link CodecPolicy#LENIENT}
     * tolerates an input whose length is odd.
     *
     * <p>
     * Each decoded byte is built from a pair of hex characters. The input below has nine
     * characters: four complete hex-pairs ("aa", "bb", "cc", "dd") followed by a lone
     * trailing "e". Lenient decoding silently discards that dangling half-byte instead of
     * throwing, so only the four complete bytes are returned.
     * </p>
     */
    @Test
    void testLenientDecoding() {
        // The trailing "e" is half of a hex-pair and is dropped under the lenient policy.
        final String encoded = "aabbccdde";
        final byte[] expectedDecoded = { (byte) 0xaa, (byte) 0xbb, (byte) 0xcc, (byte) 0xdd };

        final boolean useLowerCaseAlphabet = true;
        final Base16 base16 = new Base16(useLowerCaseAlphabet, CodecPolicy.LENIENT);
        assertEquals(CodecPolicy.LENIENT, base16.getCodecPolicy(), "configured codec policy");

        final byte[] decoded = base16.decode(StringUtils.getBytesUtf8(encoded));

        assertArrayEquals(expectedDecoded, decoded, "lenient decoding drops the trailing half-byte");
    }
}
