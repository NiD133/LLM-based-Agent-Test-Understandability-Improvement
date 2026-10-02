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
public class SeekableInMemoryByteChannel_ESTest_test08 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    private static final long EMPTY_CHANNEL_SIZE = 0L;
    private static final int READ_BUFFER_CAPACITY = 832;
    private static final int END_OF_CHANNEL = -1;

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.truncate(EMPTY_CHANNEL_SIZE);

        ByteBuffer readTarget = ByteBuffer.allocateDirect(READ_BUFFER_CAPACITY);
        int bytesRead = channel.read(readTarget);

        assertEquals(END_OF_CHANNEL, bytesRead);
    }
}
