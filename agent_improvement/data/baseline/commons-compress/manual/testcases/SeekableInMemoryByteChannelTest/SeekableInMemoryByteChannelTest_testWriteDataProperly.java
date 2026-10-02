package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testWriteDataProperly {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testWriteDataProperly() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel()) {
            final ByteBuffer inData = ByteBuffer.wrap(testData);
            final int writeCount = c.write(inData);
            assertEquals(testData.length, writeCount);
            assertEquals(testData.length, c.position());
            assertArrayEquals(testData, Arrays.copyOf(c.array(), (int) c.position()));
        }
    }
}
