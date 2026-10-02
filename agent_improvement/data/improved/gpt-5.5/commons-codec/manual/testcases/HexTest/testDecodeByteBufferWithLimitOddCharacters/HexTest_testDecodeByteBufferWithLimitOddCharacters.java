package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.ByteBuffer;

import org.apache.commons.codec.DecoderException;
import org.junit.jupiter.api.Test;

public class HexTest_testDecodeByteBufferWithLimitOddCharacters {

    protected ByteBuffer allocate(final int capacity) {
        return ByteBuffer.allocate(capacity);
    }

    private void assertDecodeRejectsOddNumberOfHexCharacters(final ByteBuffer data) {
        assertThrows(DecoderException.class, () -> new Hex().decode(data));
    }

    @Test
    void testDecodeByteBufferWithLimitOddCharacters() {
        final ByteBuffer bufferWithOneRemainingCharacter = allocate(10);
        bufferWithOneRemainingCharacter.put(1, (byte) 65);
        bufferWithOneRemainingCharacter.position(1);
        bufferWithOneRemainingCharacter.limit(2);

        assertDecodeRejectsOddNumberOfHexCharacters(bufferWithOneRemainingCharacter);
    }
}
