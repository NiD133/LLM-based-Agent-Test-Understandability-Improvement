package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test18 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * A freshly constructed stream should report that mark/reset is supported and
     * should make all of its backing bytes available for reading.
     */
    @Test(timeout = 4000)
    public void markSupportedAndFullBufferAvailable() throws Throwable {
        byte[] sourceBytes = new byte[2];
        UnsynchronizedByteArrayInputStream inputStream =
                new UnsynchronizedByteArrayInputStream(sourceBytes);

        boolean markSupported = inputStream.markSupported();

        assertTrue("mark/reset must be supported", markSupported);
        assertEquals("all backing bytes should be available", 2, inputStream.available());
    }
}
