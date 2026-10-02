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
public class SeekableInMemoryByteChannel_ESTest_test02 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    // A position that exceeds Integer.MAX_VALUE (2147483647), triggering the overflow guard in write()
    private static final long POSITION_BEYOND_INT_MAX = 2147483651L;

    private static final int WRITE_BUFFER_SIZE = 52;

    @Test(timeout = 4000)
    public void test_writeFails_whenPositionExceedsIntegerMaxValue() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        // Advance the channel position past Integer.MAX_VALUE so that write() hits
        // its "position > Integer.MAX_VALUE" guard and throws IOException.
        channel.position(POSITION_BEYOND_INT_MAX);

        ByteBuffer writeBuffer = ByteBuffer.allocate(WRITE_BUFFER_SIZE);
        try {
            channel.write(writeBuffer);
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // position > Integer.MAX_VALUE
            //
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
