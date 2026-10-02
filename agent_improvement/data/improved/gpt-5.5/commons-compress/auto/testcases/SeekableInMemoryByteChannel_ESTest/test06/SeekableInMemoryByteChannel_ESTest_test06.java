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
    public void test06() throws Throwable {
        final int initialChannelSize = 0;
        final int sourceBufferCapacity = 2064;

        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(initialChannelSize);
        ByteBuffer sourceBuffer = ByteBuffer.allocateDirect(sourceBufferCapacity);

        int bytesWritten = channel.write(sourceBuffer);

        assertEquals(0, sourceBuffer.remaining());
        assertEquals(sourceBufferCapacity, bytesWritten);
    }
}
