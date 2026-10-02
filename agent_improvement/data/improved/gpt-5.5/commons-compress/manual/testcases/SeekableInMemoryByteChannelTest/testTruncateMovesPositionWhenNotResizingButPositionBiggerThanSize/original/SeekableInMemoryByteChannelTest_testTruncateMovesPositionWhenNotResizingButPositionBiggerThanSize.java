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

public class SeekableInMemoryByteChannelTest_testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize {

    private final byte[] testData = "Some data".getBytes(StandardCharsets.UTF_8);

    /*
     * <q> In either case, if the current position is greater than the given size then it is set to that size.</q>
     */
    @Test
    void testTruncateMovesPositionWhenNotResizingButPositionBiggerThanSize() throws Exception {
        try (SeekableByteChannel c = new SeekableInMemoryByteChannel(testData)) {
            c.position(2 * testData.length);
            c.truncate(testData.length);
            assertEquals(testData.length, c.size());
            assertEquals(testData.length, c.position());
        }
    }
}
