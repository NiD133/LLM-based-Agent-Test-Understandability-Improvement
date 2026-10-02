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
public class SeekableInMemoryByteChannel_ESTest_test01 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Writing a full buffer into a fresh channel should consume every remaining
     * byte: the return value equals the buffer capacity, and the buffer's
     * position is advanced to its limit (leaving nothing remaining).
     */
    @Test(timeout = 4000)
    public void writeConsumesEntireBufferAndReturnsByteCount() throws Throwable {
        final int bufferCapacity = 2245;
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        ByteBuffer sourceBuffer = ByteBuffer.allocate(bufferCapacity);

        int bytesWritten = channel.write(sourceBuffer);

        assertEquals("All bytes from the buffer should be written", bufferCapacity, bytesWritten);
        assertEquals("Buffer position should be fully advanced to its limit",
                "java.nio.HeapByteBuffer[pos=2245 lim=2245 cap=2245]", sourceBuffer.toString());
    }
}
