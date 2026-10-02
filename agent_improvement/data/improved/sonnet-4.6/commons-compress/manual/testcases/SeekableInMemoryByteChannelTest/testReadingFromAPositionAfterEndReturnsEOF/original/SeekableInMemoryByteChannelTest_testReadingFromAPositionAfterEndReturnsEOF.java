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

public class SeekableInMemoryByteChannelTest_testReadingFromAPositionAfterEndReturnsEOF {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to read
     * bytes at such a position will immediately return an end-of-file indication</q>
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int size) throws Exception {
        try (SeekableByteChannel c = new SeekableInMemoryByteChannel(size)) {
            final int position = 2;
            c.position(position);
            assertEquals(position, c.position());
            final int readSize = 5;
            final ByteBuffer readBuffer = ByteBuffer.allocate(readSize);
            assertEquals(position >= size ? -1 : size - position, c.read(readBuffer));
        }
    }
}
