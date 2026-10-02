package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link SeekableInMemoryByteChannel} handles positions that lie outside the data
 * actually stored in the channel.
 *
 * <p>The contract under test is:</p>
 * <ul>
 *   <li>A position past the end of the data is accepted; reading from there simply returns -1
 *       (end of stream).</li>
 *   <li>A position beyond {@link Integer#MAX_VALUE} is also accepted, reads still return -1, but
 *       any write from there fails with an {@link IOException}.</li>
 *   <li>A negative position is the only value rejected up front, with an
 *       {@link IllegalArgumentException}.</li>
 * </ul>
 */
public class SeekableInMemoryByteChannelTest_testThrowWhenSettingIncorrectPosition {

    @Test
    void testThrowWhenSettingIncorrectPosition() throws IOException {
        try (SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel()) {
            final ByteBuffer oneByteBuffer = ByteBuffer.allocate(1);

            // Write a single byte so the channel holds some data and the cursor sits at the end.
            channel.write(oneByteBuffer);
            assertEquals(1, channel.position());

            // Position just past the stored data: allowed, but there is nothing left to read.
            final long positionPastData = channel.size() + 1;
            channel.position(positionPastData);
            assertEquals(positionPastData, channel.position());
            assertEquals(-1, channel.read(oneByteBuffer));

            // Position beyond Integer.MAX_VALUE: still allowed and still returns -1 on read,
            // but the channel cannot write at such a position and reports an IOException.
            final long positionBeyondIntMax = Integer.MAX_VALUE + 1L;
            channel.position(positionBeyondIntMax);
            assertEquals(positionBeyondIntMax, channel.position());
            assertEquals(-1, channel.read(oneByteBuffer));
            assertThrows(IOException.class, () -> channel.write(oneByteBuffer));

            // A negative position is the only value rejected outright.
            assertThrows(IllegalArgumentException.class, () -> channel.position(-1));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Integer.MIN_VALUE));
            assertThrows(IllegalArgumentException.class, () -> channel.position(Long.MIN_VALUE));
        }
    }
}
