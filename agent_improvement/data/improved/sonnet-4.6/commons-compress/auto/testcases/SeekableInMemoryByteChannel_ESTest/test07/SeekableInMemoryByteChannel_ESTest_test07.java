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
public class SeekableInMemoryByteChannel_ESTest_test07 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Verifies that writing a full direct ByteBuffer to a truncated (empty) channel
     * consumes all buffer bytes and reports the correct written byte count.
     *
     * Steps:
     *  1. Create a fresh channel and truncate it to size 0 so the logical content is empty.
     *  2. Allocate a direct ByteBuffer of 832 bytes (all bytes remain as "remaining").
     *  3. Write the buffer to the channel — the channel must accept all 832 bytes.
     *  4. After the write, the buffer's position must equal 832 (all bytes consumed),
     *     and the return value must equal 832 (number of bytes written).
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.truncate(0L);

        final int bufferCapacity = 832;
        ByteBuffer sourceBuffer = ByteBuffer.allocateDirect(bufferCapacity);

        int bytesWritten = channel.write(sourceBuffer);

        assertEquals("Buffer position should advance to its capacity after a full write",
                bufferCapacity, sourceBuffer.position());
        assertEquals("Number of bytes written should equal the buffer capacity",
                bufferCapacity, bytesWritten);
    }
}
