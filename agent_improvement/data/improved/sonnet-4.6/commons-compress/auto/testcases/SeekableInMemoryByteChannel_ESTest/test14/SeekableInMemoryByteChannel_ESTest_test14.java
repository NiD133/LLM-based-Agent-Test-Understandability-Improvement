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
public class SeekableInMemoryByteChannel_ESTest_test14 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Verifies that reading from a closed channel throws ClosedChannelException.
     * A channel must reject all I/O operations once it has been closed.
     */
    @Test(timeout = 4000)
    public void test_readOnClosedChannel_throwsClosedChannelException() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();
        channel.close();

        try {
            channel.read((ByteBuffer) null);
            fail("Expecting exception: ClosedChannelException");
        } catch (ClosedChannelException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
