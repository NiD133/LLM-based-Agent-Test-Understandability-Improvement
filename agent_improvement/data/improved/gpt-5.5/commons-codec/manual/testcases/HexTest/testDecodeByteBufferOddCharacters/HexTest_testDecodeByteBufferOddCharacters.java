package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferOddCharacters {

    private static final int ODD_NUMBER_OF_HEX_CHARACTERS = 1;
    private static final byte SINGLE_HEX_CHARACTER = 65;

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    private void checkDecodeHexByteBufferOddCharacters(final ByteBuffer data) {
        assertThrows(DecoderException.class, () -> new Hex().decode(data));
    }

    @Test
    void testDecodeByteBufferOddCharacters() {
        final ByteBuffer bb = allocate(ODD_NUMBER_OF_HEX_CHARACTERS);
        bb.put(SINGLE_HEX_CHARACTER);
        bb.flip();

        checkDecodeHexByteBufferOddCharacters(bb);
    }
}
