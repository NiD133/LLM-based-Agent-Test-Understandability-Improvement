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
public class SeekableInMemoryByteChannel_ESTest_test10 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    private static final int BUFFER_CAPACITY = 832;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        ByteBuffer destination = ByteBuffer.allocateDirect(BUFFER_CAPACITY);

        int bytesRead = channel.read(destination);

        assertEquals(0, destination.remaining());
        assertEquals(BUFFER_CAPACITY, bytesRead);
    }
}
