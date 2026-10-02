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

    // Verifies that reading from a channel truncated to zero bytes returns -1 (EOF),
    // regardless of the destination buffer capacity.
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        SeekableInMemoryByteChannel emptyChannel = new SeekableInMemoryByteChannel();
        emptyChannel.truncate(0L);

        ByteBuffer largeBuffer = ByteBuffer.allocateDirect(832);
        int bytesRead = emptyChannel.read(largeBuffer);

        assertEquals((-1), bytesRead);
    }
}
