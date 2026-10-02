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

public class SeekableInMemoryByteChannelTest_testWritingToAPositionAfterEndGrowsChannel {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to write
     * bytes at such a position will cause the entity to grow to accommodate the new bytes; the values of any bytes between the previous end-of-file and the
     * newly-written bytes are unspecified.</q>
     */
    @Test
    void testWritingToAPositionAfterEndGrowsChannel() throws Exception {
        try (SeekableByteChannel c = new SeekableInMemoryByteChannel()) {
            c.position(2);
            assertEquals(2, c.position());
            final ByteBuffer inData = ByteBuffer.wrap(testData);
            assertEquals(testData.length, c.write(inData));
            assertEquals(8_192, c.size());
            c.position(2);
            final ByteBuffer readBuffer = ByteBuffer.allocate(testData.length);
            c.read(readBuffer);
            assertArrayEquals(testData, Arrays.copyOf(readBuffer.array(), testData.length));
        }
    }
}
