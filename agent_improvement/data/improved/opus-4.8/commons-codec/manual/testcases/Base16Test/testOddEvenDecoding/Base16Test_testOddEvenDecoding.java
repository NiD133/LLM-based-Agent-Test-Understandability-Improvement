package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testOddEvenDecoding {

    /**
     * Verifies that the streaming {@link Base16#decode} can be fed its input in
     * chunks of varying (odd and even) sizes and still reconstruct the original
     * bytes. The shared {@link BaseNCodec.Context} carries any leftover half-byte
     * between calls, so splitting the encoded text mid-pair must not corrupt the result.
     */
    @Test
    void testOddEvenDecoding() {
        // "4142434445" is the Base16 encoding of the ASCII string "ABCDE".
        final String encoded = "4142434445";
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        final Base16 base16 = new Base16();
        final BaseNCodec.Context context = new BaseNCodec.Context();

        // Feed the encoded bytes in odd, then even, then odd sized chunks.
        base16.decode(encodedBytes, 0, 3, context); // chars [0..3)  (odd)
        base16.decode(encodedBytes, 3, 4, context); // chars [3..7)  (even)
        base16.decode(encodedBytes, 7, 3, context); // chars [7..10) (odd)

        // Collect the decoded bytes accumulated in the context's buffer.
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);

        final String decoded = StringUtils.newStringUtf8(decodedBytes);
        assertEquals("ABCDE", decoded);
    }
}
