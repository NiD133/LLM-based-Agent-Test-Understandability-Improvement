package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test01 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        final int streamLength = 6;
        final long bytesToSkip = 0L;

        final byte[] sourceBytes = new byte[streamLength];
        final UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(sourceBytes);

        final long skippedByteCount = inputStream.skip(bytesToSkip);

        assertEquals(0L, skippedByteCount);
        assertEquals(streamLength, inputStream.available());
    }
}
