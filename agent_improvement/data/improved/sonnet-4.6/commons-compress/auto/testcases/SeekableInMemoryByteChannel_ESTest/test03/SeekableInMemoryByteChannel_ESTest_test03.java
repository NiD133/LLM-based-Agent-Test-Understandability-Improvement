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
public class SeekableInMemoryByteChannel_ESTest_test03 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    // A position value just below Integer.MAX_VALUE (2147483647 - 19)
    private static final long NEAR_MAX_POSITION = 2147483628L;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Create an empty in-memory channel
        SeekableInMemoryByteChannel seekableInMemoryByteChannel0 = new SeekableInMemoryByteChannel();

        // Advance the position to a large value near Integer.MAX_VALUE
        SeekableByteChannel seekableByteChannel0 = seekableInMemoryByteChannel0.position(NEAR_MAX_POSITION);

        // Truncate the channel to size 1; since position > 1, position is also reset to 1
        SeekableByteChannel seekableByteChannel1 = seekableByteChannel0.truncate(1);

        // The channel must remain open after repositioning and truncation
        assertTrue(seekableByteChannel1.isOpen());
    }
}
