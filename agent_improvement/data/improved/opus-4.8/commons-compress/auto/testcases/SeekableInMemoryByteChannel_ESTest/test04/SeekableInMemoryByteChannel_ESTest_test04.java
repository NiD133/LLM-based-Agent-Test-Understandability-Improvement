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
public class SeekableInMemoryByteChannel_ESTest_test04 extends SeekableInMemoryByteChannel_ESTest_scaffolding {

    /**
     * Verifies that {@link SeekableInMemoryByteChannel#truncate(long)} returns the
     * same channel instance it was called on, allowing the call to be chained.
     * Truncating to size 0 is also confirmed to be repeatable.
     */
    @Test(timeout = 4000)
    public void truncateReturnsSameChannelInstance() throws Throwable {
        SeekableInMemoryByteChannel channel = new SeekableInMemoryByteChannel();

        // A first truncate to 0 should succeed without altering the returned instance.
        channel.truncate(0L);

        // truncate must return the very same channel instance (fluent self-reference).
        SeekableByteChannel returnedChannel = channel.truncate(0L);
        assertSame(channel, returnedChannel);
    }
}
