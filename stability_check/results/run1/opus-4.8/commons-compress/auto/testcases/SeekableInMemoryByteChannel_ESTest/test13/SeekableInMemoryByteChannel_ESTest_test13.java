package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SeekableInMemoryByteChannel_ESTest_test13 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * A channel constructed with a capacity of zero bytes should report a size of zero,
     * since no data has been written to it yet.
     */
    @Test(timeout = 4000)
    public void sizeOfChannelCreatedWithZeroCapacityIsZero() throws Throwable {
        SeekableInMemoryByteChannel emptyChannel = new SeekableInMemoryByteChannel(0);

        long size = emptyChannel.size();

        assertEquals(0L, size);
    }
}
