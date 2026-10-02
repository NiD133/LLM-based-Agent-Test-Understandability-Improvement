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
public class SeekableInMemoryByteChannel_ESTest_test06 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Writing a buffer larger than the channel's initial capacity should grow the
     * channel to fit, consume the entire buffer, and return the number of bytes written.
     */
    @Test(timeout = 4000)
    public void writeGrowsChannelAndConsumesEntireBuffer() throws Throwable {
        final int bytesToWrite = 2064;
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(0);
        ByteBuffer source = ByteBuffer.allocateDirect(bytesToWrite);

        int bytesWritten = channel.write(source);

        assertEquals("entire buffer should be consumed", 0, source.remaining());
        assertEquals("all bytes should be written", bytesToWrite, bytesWritten);
    }
}
