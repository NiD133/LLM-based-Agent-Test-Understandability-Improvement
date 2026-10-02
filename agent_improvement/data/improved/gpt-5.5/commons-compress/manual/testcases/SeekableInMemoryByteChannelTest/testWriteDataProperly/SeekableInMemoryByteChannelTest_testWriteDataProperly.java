package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class SeekableInMemoryByteChannelTest_testWriteDataProperly {

    private static final byte[] TEST_DATA = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWriteDataProperly() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer inputBuffer = ByteBuffer.wrap(TEST_DATA);

            final int bytesWritten = channel.write(inputBuffer);

            assertEquals(TEST_DATA.length, bytesWritten);
            assertEquals(TEST_DATA.length, channel.position());
            assertArrayEquals(TEST_DATA, Arrays.copyOf(channel.array(), (int) channel.position()));
        }
    }
}
