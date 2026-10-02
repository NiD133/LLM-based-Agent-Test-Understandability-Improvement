package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testOddEvenDecoding {

    @Test
    void testOddEvenDecoding() {
        // "4142434445" is the Base16 (hex) encoding of "ABCDE":
        //   'A'=0x41, 'B'=0x42, 'C'=0x43, 'D'=0x44, 'E'=0x45
        // The string is 10 chars (5 hex pairs). We deliberately split it into
        // three chunks with odd, even, and odd lengths to exercise the
        // half-byte carry logic inside Base16.decode().
        final String encoded = "4142434445";
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);

        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 base16 = new Base16();

        // Chunk 1: bytes [0..2] → "414" (3 chars, odd)
        base16.decode(encodedBytes, 0, 3, context);
        // Chunk 2: bytes [3..6] → "2434" (4 chars, even)
        base16.decode(encodedBytes, 3, 4, context);
        // Chunk 3: bytes [7..9] → "445" (3 chars, odd)
        base16.decode(encodedBytes, 7, 3, context);

        // Extract the decoded bytes from the context's internal buffer
        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);

        final String decoded = StringUtils.newStringUtf8(decodedBytes);
        assertEquals("ABCDE", decoded);
    }
}
