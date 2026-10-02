package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test07 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final byte[] sourceBuffer = new byte[3];
        final int startOffset = 1;
        final int readableLength = 1;
        final int expectedZeroByte = 0;
        final int expectedBytesRemainingAfterRead = 0;

        final UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(sourceBuffer, startOffset, readableLength);

        final int actualByte = stream.read();

        assertEquals(expectedBytesRemainingAfterRead, stream.available());
        assertEquals(expectedZeroByte, actualByte);
    }
}
