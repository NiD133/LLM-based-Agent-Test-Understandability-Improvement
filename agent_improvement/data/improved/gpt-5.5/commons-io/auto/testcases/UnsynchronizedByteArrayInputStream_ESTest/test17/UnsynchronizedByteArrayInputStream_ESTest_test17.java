package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test17 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        byte[] buffer = new byte[2];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(buffer);

        int bytesRead = inputStream.read(buffer);

        assertEquals(0, inputStream.available());
        assertEquals(2, bytesRead);
    }
}
