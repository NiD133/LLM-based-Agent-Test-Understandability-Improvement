package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test09 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final int bufferLength = 2;
        final int streamOffset = 1;
        final int streamLength = 1;
        final int expectedAvailableBytes = 1;

        byte[] buffer = new byte[bufferLength];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(buffer, streamOffset, streamLength);

        int availableBytes = inputStream.available();

        assertEquals(expectedAvailableBytes, availableBytes);
    }
}
