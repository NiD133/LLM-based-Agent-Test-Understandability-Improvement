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
            final ByteBuffer singleByteBuffer = ByteBuffer.allocate(1);

            // Write one byte to establish a channel size of 1
            channel.write(singleByteBuffer);
            assertEquals(1, channel.position());

            // A position beyond the current size is legal to set, but reads return -1
            // because there is no data at that position
            long positionBeyondSize = channel.size() + 1;
            channel.position(positionBeyondSize);
            assertEquals(positionBeyondSize, channel.position());
            assertEquals(-1, channel.read(singleByteBuffer));

            // A position exceeding Integer.MAX_VALUE is also legal to set, but reads
            // return -1 and writes throw IOException since the internal buffer cannot
            // address memory beyond that index
            long positionBeyondIntMax = Integer.MAX_VALUE + 1L;
            channel.position(positionBeyondIntMax);
            assertEquals(positionBeyondIntMax, channel.position());
            assertEquals(-1, channel.read(singleByteBuffer));
            assertThrows(IOException.class, () -> channel.write(singleByteBuffer));

            // Only negative positions are truly illegal and throw IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Long.MIN_VALUE));
        }
    }
}
