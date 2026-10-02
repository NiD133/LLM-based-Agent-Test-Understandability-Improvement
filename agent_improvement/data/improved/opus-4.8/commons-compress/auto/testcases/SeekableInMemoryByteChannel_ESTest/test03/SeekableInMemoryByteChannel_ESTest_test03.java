package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SeekableByteChannel;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SeekableInMemoryByteChannel_ESTest_test03 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Truncating a channel after moving its position to a large offset still
     * leaves the channel open, because this implementation never closes on
     * position/truncate operations.
     */
    @Test(timeout = 4000)
    public void truncateAfterSettingLargePositionKeepsChannelOpen() throws Throwable {
        final long largePosition = 2147483628L;
        final long newSize = 1L;

        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        SeekableByteChannel positionedChannel = channel.position(largePosition);
        SeekableByteChannel truncatedChannel = positionedChannel.truncate(newSize);

        assertTrue(truncatedChannel.isOpen());
    }
}
