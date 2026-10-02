package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test20 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        final int bufferLength = 8;
        final int offsetBeyondEndOfBuffer = 358;
        final int ignoredReadLimit = -1429;

        final byte[] buffer = new byte[bufferLength];
        final UnsynchronizedByteArrayInputStream stream =
                new UnsynchronizedByteArrayInputStream(buffer, offsetBeyondEndOfBuffer);

        stream.mark(ignoredReadLimit);

        assertEquals(0, stream.available());
    }
}
