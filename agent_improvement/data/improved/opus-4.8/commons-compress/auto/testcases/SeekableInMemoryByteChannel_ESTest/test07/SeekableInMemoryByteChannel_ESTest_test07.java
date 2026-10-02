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
     * Writing a full buffer should consume every remaining byte: the method
     * returns the number of bytes written and the source buffer's position
     * advances to its limit.
     */
    @Test(timeout = 4000)
    public void writeConsumesEntireBufferAndReturnsByteCount() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.truncate(0L);

        final int bufferCapacity = 832;
        ByteBuffer source = ByteBuffer.allocateDirect(bufferCapacity);

        int bytesWritten = channel.write(source);

        assertEquals("all bytes should be written", bufferCapacity, bytesWritten);
        assertEquals("buffer position should advance to its limit", bufferCapacity, source.position());
    }
}
