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

public class SeekableInMemoryByteChannelTest_testThrowWhenSettingIncorrectPosition {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    @Test
    void testThrowWhenSettingIncorrectPosition() throws IOException {
        try (SeekableInMemoryByteChannel c = new SeekableInMemoryByteChannel()) {
            final ByteBuffer buffer = ByteBuffer.allocate(1);
            // write
            c.write(buffer);
            assertEquals(1, c.position());
            // bad pos A
            c.position(c.size() + 1);
            assertEquals(c.size() + 1, c.position());
            assertEquals(-1, c.read(buffer));
            // bad pos B
            c.position(Integer.MAX_VALUE + 1L);
            assertEquals(Integer.MAX_VALUE + 1L, c.position());
            assertEquals(-1, c.read(buffer));
            assertThrows(IOException.class, () -> c.write(buffer));
            // negative input is the only illegal input
            assertThrows(IllegalArgumentException.class, () -> c.position(-1));
            assertThrows(IllegalArgumentException.class, () -> c.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> c.position(Long.MIN_VALUE));
        }
    }
}
