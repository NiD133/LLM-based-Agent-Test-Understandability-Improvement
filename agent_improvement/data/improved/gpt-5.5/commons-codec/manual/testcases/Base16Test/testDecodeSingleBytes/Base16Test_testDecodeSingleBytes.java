package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testDecodeSingleBytes {

    private static final String ENCODED_TEXT = "556E74696C206E6578742074696D6521";
    private static final String DECODED_TEXT = "Until next time!";

    @Test
    void testDecodeSingleBytes() {
        final Base16 base16 = new Base16();
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final byte[] encodedBytes = StringUtils.getBytesUtf8(ENCODED_TEXT);

        // Feed the decoder in the same uneven chunks used by the original test.
        // This covers both byte-by-byte input and split Base16 character pairs.
        base16.decode(encodedBytes, 0, 1, context);
        base16.decode(encodedBytes, 1, 1, context);
        base16.decode(encodedBytes, 2, 1, context);
        base16.decode(encodedBytes, 3, 1, context);
        base16.decode(encodedBytes, 4, 3, context);
        base16.decode(encodedBytes, 7, 3, context);
        base16.decode(encodedBytes, 10, 3, context);
        base16.decode(encodedBytes, 13, 19, context);

        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);

        assertEquals(DECODED_TEXT, StringUtils.newStringUtf8(decodedBytes));
    }
}
