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

    // The channel holds 2245 bytes; the buffer is larger (2402), so a single read drains the channel.
    // After the read: bytesRead == 2245, and 2402 - 2245 == 157 positions remain in the buffer.
    private static final int CHANNEL_SIZE = 2245;
    private static final int BUFFER_CAPACITY = 2402;
    private static final int EXPECTED_BYTES_READ = CHANNEL_SIZE;
    private static final int EXPECTED_BUFFER_REMAINING = BUFFER_CAPACITY - CHANNEL_SIZE; // 157

    @Test(timeout = 4000)
    public void test00_readFromChannelIntoLargerBuffer_readsAllChannelBytes() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(CHANNEL_SIZE);
        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_CAPACITY);

        int bytesRead = channel.read(buffer);

        assertEquals(EXPECTED_BUFFER_REMAINING, buffer.remaining());
        assertEquals(EXPECTED_BYTES_READ, bytesRead);
    }
}
