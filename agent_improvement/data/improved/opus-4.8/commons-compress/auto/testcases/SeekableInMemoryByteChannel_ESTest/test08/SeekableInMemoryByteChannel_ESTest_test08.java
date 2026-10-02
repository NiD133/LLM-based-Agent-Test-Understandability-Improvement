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

    /**
     * Reading from an empty channel returns -1 (end-of-stream).
     *
     * <p>A default channel is truncated to size 0, so there are no bytes
     * available. Reading into a buffer should therefore report -1 rather
     * than transferring any data.</p>
     */
    @Test(timeout = 4000)
    public void readFromEmptyChannelReturnsEndOfStream() throws Throwable {
        SeekableInMemoryByteChannel emptyChannel = new SeekableInMemoryByteChannel();
        emptyChannel.truncate(0L);

        ByteBuffer destination = ByteBuffer.allocateDirect(832);
        int bytesRead = emptyChannel.read(destination);

        assertEquals(-1, bytesRead);
    }
}
