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

    /**
     * Reading into a buffer smaller than the channel's contents should fill the
     * buffer completely: the default channel holds 8192 zero bytes, so a 832-byte
     * read consumes the whole buffer and returns 832.
     */
    @Test(timeout = 4000)
    public void readFillsBufferAndReturnsBytesRead() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        int destinationCapacity = 832;
        ByteBuffer destination = ByteBuffer.allocateDirect(destinationCapacity);
        int bytesRead = channel.read(destination);

        assertEquals("buffer should be filled, leaving no space remaining", 0, destination.remaining());
        assertEquals("read should report the full buffer was filled", destinationCapacity, bytesRead);
    }
}
