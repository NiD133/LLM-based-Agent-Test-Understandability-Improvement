package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testThrowWhenTruncatingToIncorrectSize {

    private static final int SINGLE_BYTE_READ = 1;
    private static final int NO_BYTES_READ = 0;

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer singleByteBuffer = ByteBuffer.allocate(SINGLE_BYTE_READ);

            channel.truncate(channel.size() + 1);
            assertEquals(SINGLE_BYTE_READ, channel.read(singleByteBuffer));

            channel.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(NO_BYTES_READ, channel.read(singleByteBuffer));

            assertThrows(IllegalArgumentException.class, () -> channel.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.truncate(Long.MIN_VALUE));
        }
    }
}
