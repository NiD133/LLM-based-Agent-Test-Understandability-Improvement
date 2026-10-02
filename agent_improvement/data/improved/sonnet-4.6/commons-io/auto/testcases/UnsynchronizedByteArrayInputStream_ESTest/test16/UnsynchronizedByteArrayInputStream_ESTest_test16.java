package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test16 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    // Verify that reset() restores the stream to the beginning so all bytes remain available.
    @Test(timeout = 4000)
    public void test16_resetRestoresStreamToStart_allBytesAvailable() throws Throwable {
        byte[] twoByteBuffer = new byte[2];
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(twoByteBuffer);

        stream.reset();

        assertEquals(2, stream.available());
    }
}
