package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testOddEvenDecoding {

    private static final String ENCODED_ASCII_LETTERS = "4142434445";
    private static final String DECODED_ASCII_LETTERS = "ABCDE";

    @Test
    void testOddEvenDecoding() {
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 base16 = new Base16();
        final byte[] encodedBytes = StringUtils.getBytesUtf8(ENCODED_ASCII_LETTERS);

        // Feed odd, then even, then odd amounts of data to exercise carry-over
        // of a half-decoded byte between decode calls.
        base16.decode(encodedBytes, 0, 3, context);
        base16.decode(encodedBytes, 3, 4, context);
        base16.decode(encodedBytes, 7, 3, context);

        final byte[] decodedBytes = copyDecodedBytes(context);
        final String decoded = StringUtils.newStringUtf8(decodedBytes);
        assertEquals(DECODED_ASCII_LETTERS, decoded);
    }

    private static byte[] copyDecodedBytes(final BaseNCodec.Context context) {
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);
        return decodedBytes;
    }
}
