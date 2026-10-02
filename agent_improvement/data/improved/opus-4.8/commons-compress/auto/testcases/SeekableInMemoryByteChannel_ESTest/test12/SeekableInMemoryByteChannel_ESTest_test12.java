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
public class SeekableInMemoryByteChannel_ESTest_test12 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * The no-arg constructor allocates a backing array of the default buffer size
     * (IOUtils.DEFAULT_BUFFER_SIZE = 8192 bytes), which array() exposes directly.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        byte[] backingArray = channel.array();

        int expectedDefaultBufferSize = 8192;
        assertEquals(expectedDefaultBufferSize, backingArray.length);
    }
}
