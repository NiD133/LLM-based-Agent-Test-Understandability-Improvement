package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Base16Test_testDecodeSingleBytes {

    /**
     * Verifies that Base16.decode() correctly accumulates output when fed data in
     * arbitrary-sized chunks, including single bytes and chunks that split hex pairs.
     *
     * The hex string "556E74696C206E6578742074696D6521" decodes to "Until next time!".
     * Each pair of hex characters represents one decoded byte:
     *   55='U'  6E='n'  74='t'  69='i'  6C='l'  20=' '  6E='n'  65='e'
     *   78='x'  74='t'  20=' '  74='t'  69='i'  6D='m'  65='e'  21='!'
     */
    @Test
    void testDecodeSingleBytes() {
        final String encoded = "556E74696C206E6578742074696D6521";
        final byte[] encodedBytes = StringUtils.getBytesUtf8(encoded);
        final BaseNCodec.Context context = new BaseNCodec.Context();
        final Base16 b16 = new Base16();

        // Feed one hex character at a time; the codec accumulates half-bytes across calls.
        // Offset 0: '5' — first nibble of "55" ('U'), stored as a half-byte
        b16.decode(encodedBytes, 0, 1, context);
        // Offset 1: '5' — second nibble; combined with stored half-byte to emit 'U'
        b16.decode(encodedBytes, 1, 1, context);
        // Offset 2: '6' — first nibble of "6E" ('n'), stored as a half-byte
        b16.decode(encodedBytes, 2, 1, context);
        // Offset 3: 'E' — second nibble; combined with stored half-byte to emit 'n'
        b16.decode(encodedBytes, 3, 1, context);

        // Feed chunks that split across hex-pair boundaries.
        // Offset 4, length 3: "746" — emits 't' (from "74"), stores '6' (first nibble of "69")
        b16.decode(encodedBytes, 4, 3, context);
        // Offset 7, length 3: "96C" — combines stored '6' with '9' to emit 'i', then emits 'l' (from "6C")
        b16.decode(encodedBytes, 7, 3, context);
        // Offset 10, length 3: "206" — emits ' ' (from "20"), stores '6' (first nibble of "6E")
        b16.decode(encodedBytes, 10, 3, context);

        // Feed the remaining 19 bytes in one call; combines stored '6' with 'E' to emit 'n',
        // then decodes "6578742074696D6521" to complete "ext time!"
        b16.decode(encodedBytes, 13, 19, context);

        final byte[] decodedBytes = new byte[context.pos];
        System.arraycopy(context.buffer, context.readPos, decodedBytes, 0, decodedBytes.length);
        assertEquals("Until next time!", StringUtils.newStringUtf8(decodedBytes));
    }
}
