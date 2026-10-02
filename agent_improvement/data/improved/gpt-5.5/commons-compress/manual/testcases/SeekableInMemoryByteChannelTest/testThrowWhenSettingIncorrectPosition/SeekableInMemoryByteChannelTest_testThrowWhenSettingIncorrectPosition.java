package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowWhenSettingIncorrectPosition {

    @Test
    void testThrowWhenSettingIncorrectPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer oneByteBuffer = ByteBuffer.allocate(1);

            channel.write(oneByteBuffer);
            assertEquals(1, channel.position());

            final long positionJustAfterEnd = channel.size() + 1;
            channel.position(positionJustAfterEnd);
            assertEquals(positionJustAfterEnd, channel.position());
            assertEquals(-1, channel.read(oneByteBuffer));

            final long positionBeyondIntegerRange = Integer.MAX_VALUE + 1L;
            channel.position(positionBeyondIntegerRange);
            assertEquals(positionBeyondIntegerRange, channel.position());
            assertEquals(-1, channel.read(oneByteBuffer));
            assertThrows(IOException.class, () -> channel.write(oneByteBuffer));

            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Long.MIN_VALUE));
        }
    }
}
