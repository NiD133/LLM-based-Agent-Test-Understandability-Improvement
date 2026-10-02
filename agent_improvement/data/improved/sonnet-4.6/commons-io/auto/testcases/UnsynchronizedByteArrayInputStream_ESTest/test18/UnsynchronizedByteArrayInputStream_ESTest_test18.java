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

    @Test(timeout = 4000)
    public void test18_markSupportedReturnsTrueAndAvailableReflectsUnreadBytes() throws Throwable {
        byte[] twoByteBuffer = new byte[2];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(twoByteBuffer);

        boolean supportsMarking = inputStream.markSupported();

        assertTrue(supportsMarking);
        assertEquals(2, inputStream.available());
    }
}
