package org.apache.commons.compress.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SeekableInMemoryByteChannelTest_testReadingFromAPositionAfterEndReturnsEOF {

    /*
     * <q>Setting the position to a value that is greater than the current size is legal but does not change the size of the entity. A later attempt to read
     * bytes at such a position will immediately return an end-of-file indication</q>
     */
    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    void testReadingFromAPositionAfterEndReturnsEOF(final int channelSize) throws Exception {
        final int seekPosition = 2;
        final int readBufferSize = 5;

        try (SeekableByteChannel channel = new SeekableInMemoryByteChannel(channelSize)) {
            channel.position(seekPosition);
            assertEquals(seekPosition, channel.position());

            final ByteBuffer readBuffer = ByteBuffer.allocate(readBufferSize);
            final boolean positionIsAtOrPastEnd = seekPosition >= channelSize;
            final int expectedBytesRead = positionIsAtOrPastEnd ? -1 : channelSize - seekPosition;

            assertEquals(expectedBytesRead, channel.read(readBuffer));
        }
    }
}
