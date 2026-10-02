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
public class SeekableInMemoryByteChannel_ESTest_test15 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * A freshly constructed channel should start with its position at the
     * beginning of the buffer (offset 0).
     */
    @Test(timeout = 4000)
    public void newChannelStartsAtPositionZero() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        long initialPosition = channel.position();

        assertEquals(0L, initialPosition);
    }
}
