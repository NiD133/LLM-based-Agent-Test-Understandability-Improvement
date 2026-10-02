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

public class SeekableInMemoryByteChannelTest_testThrowWhenTruncatingToIncorrectSize {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testThrowWhenTruncatingToIncorrectSize() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);
            c.truncate(c.size() + 1);
            assertEquals(1, c.read(buffer));
            c.truncate(Integer.MAX_VALUE + 1L);
            assertEquals(0, c.read(buffer));
            assertThrows(IllegalArgumentException.class, () -> c.truncate(-1));
            assertThrows(IllegalArgumentException.class, () -> c.truncate(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> c.truncate(Long.MIN_VALUE));
        }
    }
}
