package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SeekableInMemoryByteChannel_ESTest_test12 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    // IOUtils.DEFAULT_BUFFER_SIZE = 8192, used by the no-arg constructor
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    @Test(timeout = 4000)
    public void testDefaultConstructorAllocatesDefaultBufferSize() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        byte[] backingArray = channel.array();

        assertEquals(DEFAULT_BUFFER_SIZE, backingArray.length);
    }
}
