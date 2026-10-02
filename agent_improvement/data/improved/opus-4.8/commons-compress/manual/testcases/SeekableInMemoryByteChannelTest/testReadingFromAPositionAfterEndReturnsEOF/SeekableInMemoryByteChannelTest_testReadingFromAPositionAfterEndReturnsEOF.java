package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testReadingFromAPositionAfterEndReturnsEOF {

    /**
     * Verifies the {@link SeekableByteChannel} contract for reads that start beyond the channel's data:
     *
     * <blockquote>Setting the position to a value that is greater than the current size is legal but does not change
     * the size of the entity. A later attempt to read bytes at such a position will immediately return an
     * end-of-file indication.</blockquote>
     *
     * <p>
     * The channel is created with a fixed read position (2) but a varying total size (0..6), so the parameter sweeps
     * the read start from past-the-end through still-inside-the-data:
     * </p>
     * <ul>
     * <li>when the read position is at or beyond the size, {@code read} must report EOF ({@code -1});</li>
     * <li>otherwise it returns the number of remaining readable bytes ({@code size - position}).</li>
     * </ul>
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int channelSize) throws Exception {
        final int readPosition = 2;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(channelSize)) {
            channel.position(readPosition);
            assertEquals(readPosition, channel.position(), "channel should report the position we set");

            final ByteBuffer readBuffer = ByteBuffer.allocate(5);
            final int expectedBytesRead = readPosition >= channelSize ? -1 : channelSize - readPosition;

            assertEquals(expectedBytesRead, channel.read(readBuffer),
                "reading at a position at/after the end should signal EOF, otherwise the remaining byte count");
        }
    }
}
