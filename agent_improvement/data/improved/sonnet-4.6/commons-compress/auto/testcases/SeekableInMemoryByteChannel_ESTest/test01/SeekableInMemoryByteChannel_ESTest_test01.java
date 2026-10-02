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
     * Verifies that writing a full ByteBuffer to the channel consumes all bytes from the buffer,
     * advancing the buffer's position to its limit, and returns the number of bytes written.
     */
    @Test(timeout = 4000)
    public void test01_writeFullBufferReturnsBufferCapacityAndExhaustsBuffer() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        ByteBuffer sourceBuffer = ByteBuffer.allocate(2245);

        int bytesWritten = channel.write(sourceBuffer);

        // After a full write, the buffer's position equals its limit (all bytes consumed).
        // The toString() output encodes pos, lim, and cap to confirm the buffer is fully drained.
        assertEquals("java.nio.HeapByteBuffer[pos=2245 lim=2245 cap=2245]", sourceBuffer.toString());
        assertEquals(2245, bytesWritten);
    }
}
