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
public class SeekableInMemoryByteChannel_ESTest_test11 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Setting the channel position to a negative value must be rejected with an
     * IllegalArgumentException carrying the message "New position is negative: -1".
     */
    @Test(timeout = 4000)
    public void positionWithNegativeValueThrowsIllegalArgumentException() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        try {
            channel.position(-1L);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "New position is negative: -1"
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
