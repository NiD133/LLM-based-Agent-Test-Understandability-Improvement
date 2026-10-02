package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test04 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    // Verifies that reading into a smaller buffer reads exactly the buffer's capacity
    // and leaves the remaining bytes available in the stream.
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        byte[] sourceData = new byte[2];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(sourceData);

        byte[] readBuffer = new byte[1];
        int bytesRead = inputStream.read(readBuffer);

        assertEquals(1, bytesRead);
        assertEquals(1, inputStream.available());
    }
}
