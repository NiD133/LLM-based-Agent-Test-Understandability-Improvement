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
public class SeekableInMemoryByteChannel_ESTest_test00 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Reading into a buffer larger than the channel's content should copy only
     * as many bytes as the channel holds, and stop there.
     */
    @Test(timeout = 4000)
    public void readCopiesOnlyAvailableBytesWhenBufferIsLarger() throws Throwable {
        final int channelSize = 2245;
        final int bufferCapacity = 2402;

        // Channel backed by 2245 zero bytes; destination buffer can hold more (2402).
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(channelSize);
        ByteBuffer destination = ByteBuffer.allocateDirect(bufferCapacity);

        int bytesRead = channel.read(destination);

        // read() is limited by the channel's content, so it returns all 2245 bytes.
        assertEquals(channelSize, bytesRead);
        // The buffer still has its leftover capacity: 2402 - 2245 = 157 bytes free.
        assertEquals(bufferCapacity - channelSize, destination.remaining());
    }
}
