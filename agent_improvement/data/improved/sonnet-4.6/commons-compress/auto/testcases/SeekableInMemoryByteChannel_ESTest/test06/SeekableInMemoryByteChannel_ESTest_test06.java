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

    @Test(timeout = 4000)
    public void test06_writeToZeroCapacityChannelExpandsAndConsumesEntireBuffer() throws Throwable {
        // Channel starts with zero pre-allocated capacity; write must grow internal storage dynamically.
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(0);

        ByteBuffer buffer = ByteBuffer.allocateDirect(2064);
        int bytesWritten = channel.write(buffer);

        // All bytes in the buffer should have been transferred to the channel.
        assertEquals(0, buffer.remaining());
        assertEquals(2064, bytesWritten);
    }
}
