package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class Base16Test_testDecodeSingleBytesOptimization {

    private static final byte HIGH_NIBBLE_HEX_CHAR = (byte) 'E';
    private static final byte LOW_NIBBLE_HEX_CHAR = (byte) 'F';
    private static final byte DECODED_BYTE = (byte) 0xEF;

    @Test
    void testDecodeSingleBytesOptimization() {
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final byte[] singleByteInput = new byte[1];
        final Base16 base16 = new Base16();

        assertEquals(0, context.ibitWorkArea);
        assertNull(context.buffer);

        singleByteInput[0] = HIGH_NIBBLE_HEX_CHAR;
        base16.decode(singleByteInput, 0, 1, context);

        assertEquals(15, context.ibitWorkArea);
        assertNull(context.buffer);

        singleByteInput[0] = LOW_NIBBLE_HEX_CHAR;
        base16.decode(singleByteInput, 0, 1, context);

        assertEquals(0, context.ibitWorkArea);
        assertEquals(DECODED_BYTE, context.buffer[0]);
    }
}
