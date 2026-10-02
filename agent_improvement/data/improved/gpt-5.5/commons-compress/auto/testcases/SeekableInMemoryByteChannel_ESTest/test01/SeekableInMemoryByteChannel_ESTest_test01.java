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

    private static final int BUFFER_CAPACITY = 2245;

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        ByteBuffer sourceBuffer = ByteBuffer.allocate(BUFFER_CAPACITY);

        int bytesWritten = channel.write(sourceBuffer);

        assertEquals("java.nio.HeapByteBuffer[pos=2245 lim=2245 cap=2245]", sourceBuffer.toString());
        assertEquals(BUFFER_CAPACITY, bytesWritten);
    }
}
