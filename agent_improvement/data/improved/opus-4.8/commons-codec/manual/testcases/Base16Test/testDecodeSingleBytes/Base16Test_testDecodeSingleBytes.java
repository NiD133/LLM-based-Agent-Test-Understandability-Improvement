package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testDecodeSingleBytes {

    /**
     * Verifies that {@link Base16#decode} can be driven incrementally: feeding the
     * encoded hex characters in small, arbitrarily-split chunks must accumulate into
     * the same plaintext as decoding them all at once.
     *
     * <p>The shared {@link BaseNCodec.Context} carries any "half byte" left over when a
     * chunk ends in the middle of a hex pair, so the next {@code decode} call can finish
     * that byte. After all chunks are processed, the decoded bytes are read out of the
     * context buffer.</p>
     */
    @Test
    void testDecodeSingleBytes() {
        // "556E74696C206E6578742074696D6521" is the Base16 (hex) encoding of "Until next time!".
        final String encodedHex = "556E74696C206E6578742074696D6521";
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encodedHex);

        final Base16 base16 = new Base16();
        final BaseNCodec.Context context = new BaseNCodec.Context();

        // Feed the encoded characters in deliberately uneven chunks to exercise the
        // incremental decoding paths (single character, split hex-pairs, and remainder).

        // Whole hex pairs, one character at a time -> "Un"
        base16.decode(encodedBytes, 0, 1, context);
        base16.decode(encodedBytes, 1, 1, context);
        base16.decode(encodedBytes, 2, 1, context);
        base16.decode(encodedBytes, 3, 1, context);

        // Odd-sized chunks that split hex-pairs across calls -> "til "
        base16.decode(encodedBytes, 4, 3, context);
        base16.decode(encodedBytes, 7, 3, context);
        base16.decode(encodedBytes, 10, 3, context);

        // Everything that remains in one chunk -> "next time!"
        base16.decode(encodedBytes, 13, 19, context);

        // Copy the decoded bytes out of the context buffer and turn them back into text.
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);
        final String decoded = StringUtils.newStringUtf8(decodedBytes);

        assertEquals("Until next time!", decoded);
    }
}
