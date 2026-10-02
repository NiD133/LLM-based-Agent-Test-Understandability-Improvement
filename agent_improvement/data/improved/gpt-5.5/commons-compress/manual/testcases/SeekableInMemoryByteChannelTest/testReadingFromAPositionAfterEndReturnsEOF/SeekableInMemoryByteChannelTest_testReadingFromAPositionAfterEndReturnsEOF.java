package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testReadingFromAPositionAfterEndReturnsEOF {

    private static final int READ_POSITION = 2;
    private static final int READ_BUFFER_SIZE = 5;
    private static final int END_OF_FILE = -1;

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int size) throws Exception {
        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(size)) {
            channel.position(READ_POSITION);

            assertEquals(READ_POSITION, channel.position());

            final ByteBuffer readBuffer = ByteBuffer.allocate(READ_BUFFER_SIZE);
            assertEquals(expectedReadResult(size), channel.read(readBuffer));
        }
    }

    private int expectedReadResult(final int size) {
        if (READ_POSITION >= size) {
            return END_OF_FILE;
        }
        return size - READ_POSITION;
    }
}
