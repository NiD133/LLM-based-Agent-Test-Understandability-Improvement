package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test19 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        final byte[] singleByteBuffer = new byte[1];
        final int offsetAtEndOfBuffer = 1;
        final int requestedLength = 1;
        final int expectedAvailableBytes = 0;

        final UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(
                singleByteBuffer,
                offsetAtEndOfBuffer,
                requestedLength);

        final int availableBytes = stream.available();

        assertEquals(expectedAvailableBytes, availableBytes);
    }
}
