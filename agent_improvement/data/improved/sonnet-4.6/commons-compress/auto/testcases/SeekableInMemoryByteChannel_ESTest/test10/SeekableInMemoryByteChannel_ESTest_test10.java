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
     * Verifies that reading from a default-constructed channel (backed by a pre-allocated
     * internal buffer) fully fills the destination ByteBuffer and returns the number of
     * bytes read equal to the buffer's capacity.
     *
     * A default SeekableInMemoryByteChannel is initialised with IOUtils.DEFAULT_BUFFER_SIZE
     * bytes (8 192), which exceeds the 832-byte destination buffer, so the read must
     * satisfy the entire request: bytesRead == bufferCapacity and buffer.remaining() == 0.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Channel backed by the default internal buffer (8 192 bytes, all pre-allocated)
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        // Destination buffer smaller than the channel's internal buffer
        final int bufferCapacity = 832;
        ByteBuffer destination = ByteBuffer.allocateDirect(bufferCapacity);

        // Read from position 0; the channel has enough data to fill the whole buffer
        int bytesRead = channel.read(destination);

        // The buffer should be completely filled (no remaining space left)
        assertEquals(0, destination.remaining());

        // The number of bytes transferred must equal the original buffer capacity
        assertEquals(bufferCapacity, bytesRead);
    }
}
