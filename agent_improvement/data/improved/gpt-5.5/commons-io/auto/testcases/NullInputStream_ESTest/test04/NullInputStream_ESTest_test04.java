package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test04 extends NullInputStream_ESTest_scaffolding {

    private static final long STREAM_SIZE = 1L;
    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean DO_NOT_THROW_ON_EOF = false;
    private static final long ZERO_BYTES_TO_SKIP = 0L;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        NullInputStream configuredStream = new NullInputStream(
                STREAM_SIZE,
                MARK_NOT_SUPPORTED,
                DO_NOT_THROW_ON_EOF);

        long skippedBytesFromSingleton = configuredStream.INSTANCE.skip(ZERO_BYTES_TO_SKIP);

        assertEquals(1, configuredStream.available());
        assertFalse(configuredStream.markSupported());
        assertEquals((-1L), skippedBytesFromSingleton);
    }
}
