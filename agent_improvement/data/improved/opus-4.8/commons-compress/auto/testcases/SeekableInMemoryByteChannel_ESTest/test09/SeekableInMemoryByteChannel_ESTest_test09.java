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
public class SeekableInMemoryByteChannel_ESTest_test09 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * When the channel's position is set beyond {@link Integer#MAX_VALUE}, a read
     * cannot return any data and must report end-of-stream by returning -1.
     */
    @Test(timeout = 4000)
    public void readReturnsEofWhenPositionExceedsIntegerMaxValue() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        long positionBeyondIntMax = Integer.MAX_VALUE + 4L;
        channel.position(positionBeyondIntMax);

        ByteBuffer destination = ByteBuffer.allocate(52);
        int bytesRead = channel.read(destination);

        assertEquals(-1, bytesRead);
    }
}
