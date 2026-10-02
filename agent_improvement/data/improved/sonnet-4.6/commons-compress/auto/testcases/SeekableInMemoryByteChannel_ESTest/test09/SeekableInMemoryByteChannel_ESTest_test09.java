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
public class SeekableInMemoryByteChannel_ESTest_test09 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    // A position value that exceeds Integer.MAX_VALUE (2147483647 + 4)
    private static final long POSITION_BEYOND_INT_MAX = 2147483651L;

    private static final int EOF = -1;

    /**
     * When the channel position is set to a value greater than Integer.MAX_VALUE,
     * read() must return -1 (EOF) because internal storage is int-indexed.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.position(POSITION_BEYOND_INT_MAX);

        ByteBuffer readBuffer = ByteBuffer.allocate((byte) 52);
        int bytesRead = channel.read(readBuffer);

        assertEquals(EOF, bytesRead);
    }
}
