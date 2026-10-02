package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies the "single byte" fast-path of {@link Base16#decode} that is taken when the codec is
 * fed one encoded character at a time.
 *
 * <p>
 * Each Base16 character encodes 4 bits, so two characters are needed to form one decoded byte.
 * When {@code decode} receives a single character, it cannot yet emit a byte; instead it parks the
 * decoded nibble in {@link BaseNCodec.Context#ibitWorkArea} (offset by +1 so that the "empty" state
 * stays 0) and waits for the next character. The following call combines the parked nibble with the
 * new one to produce the complete byte.
 * </p>
 */
public class Base16Test_testDecodeSingleBytesOptimization {

    @Test
    void testDecodeSingleBytesOptimization() {
        final Base16 base16 = new Base16();
        final BaseNCodec.Context context = new BaseNCodec.Context();

        // A fresh context holds no parked nibble and has not allocated an output buffer yet.
        assertEquals(0, context.ibitWorkArea);
        assertNull(context.buffer);

        final byte[] singleChar = new byte[1];

        // Feed the high nibble 'E' (0xE == 14). It is parked, not yet emitted, so no buffer exists.
        singleChar[0] = (byte) 'E';
        base16.decode(singleChar, 0, 1, context);
        assertEquals(14 + 1, context.ibitWorkArea, "nibble 0xE parked as value + 1");
        assertNull(context.buffer);

        // Feed the low nibble 'F'. The two nibbles combine into the byte 0xEF and the work area resets.
        singleChar[0] = (byte) 'F';
        base16.decode(singleChar, 0, 1, context);
        assertEquals(0, context.ibitWorkArea, "work area cleared after emitting a byte");
        assertEquals((byte) 0xEF, context.buffer[0], "0xE and 0xF combined into 0xEF");
    }
}
