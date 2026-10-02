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
public class SeekableInMemoryByteChannel_ESTest_test13 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    // A channel created with an explicit size of 0 should report a size of zero immediately after construction.
    @Test(timeout = 4000)
    public void test_sizeReturnsZeroWhenChannelCreatedWithZeroCapacity() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel(0);
        long actualSize = channel.size();
        assertEquals(0L, actualSize);
    }
}
