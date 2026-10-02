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
public class SeekableInMemoryByteChannel_ESTest_test05 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    private static final long NEGATIVE_SIZE = -794L;

    @Test(timeout = 4000)
    public void truncate_withNegativeSize_throwsIllegalArgumentException() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        try {
            channel.truncate(NEGATIVE_SIZE);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // truncate() rejects any negative size with a descriptive message
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
