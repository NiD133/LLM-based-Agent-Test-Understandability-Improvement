package org.apache.commons.compress.utils;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SeekableInMemoryByteChannel_ESTest_test14 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Reading from a channel that has already been closed must fail with a
     * {@link ClosedChannelException}, because {@code read} first checks that the
     * channel is still open before touching the supplied buffer.
     */
    @Test(timeout = 4000)
    public void readOnClosedChannelThrowsClosedChannelException() throws Throwable {
        SeekableInMemoryByteChannel closedChannel = new SeekableInMemoryByteChannel();
        closedChannel.close();

        try {
            closedChannel.read((ByteBuffer) null);
            fail("Expecting exception: ClosedChannelException");
        } catch (ClosedChannelException e) {
            // The open-check runs before the null buffer is ever dereferenced,
            // so we get a ClosedChannelException (with no message) rather than NPE.
            verifyException("org.apache.commons.compress.utils.SeekableInMemoryByteChannel", e);
        }
    }
}
