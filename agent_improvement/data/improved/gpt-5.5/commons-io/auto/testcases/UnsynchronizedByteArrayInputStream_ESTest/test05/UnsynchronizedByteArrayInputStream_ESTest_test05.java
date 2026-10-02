package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test05 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final byte[] buffer = new byte[8];
        final int offsetBeyondEndOfBuffer = 358;
        final UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(buffer, offsetBeyondEndOfBuffer);

        assertEquals(0, inputStream.available());

        final int bytesRead = inputStream.read(buffer);
        assertEquals((-1), bytesRead);
    }
}
