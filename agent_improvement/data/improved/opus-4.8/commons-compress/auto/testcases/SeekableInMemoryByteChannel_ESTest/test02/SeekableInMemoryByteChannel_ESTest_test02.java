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
public class SeekableInMemoryByteChannel_ESTest_test02 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Writing fails when the channel position has been set beyond Integer.MAX_VALUE,
     * because the backing buffer is a byte array that cannot be indexed past that limit.
     */
    @Test(timeout = 4000)
    public void writeThrowsIOExceptionWhenPositionExceedsIntegerMaxValue() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        // Move the position just above Integer.MAX_VALUE (2147483647).
        long positionBeyondMaxInt = 2147483651L;
        channel.position(positionBeyondMaxInt);

        ByteBuffer dataToWrite = ByteBuffer.allocate(52);
        try {
            channel.write(dataToWrite);
            fail("Expecting exception: IOException (position > Integer.MAX_VALUE)");
        } catch (IOException e) {
            // write() rejects the call with "position > Integer.MAX_VALUE".
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
