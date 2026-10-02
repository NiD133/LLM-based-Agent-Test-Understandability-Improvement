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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final int sourceLength = 2;
        final int destinationLength = 1;
        final int expectedBytesRead = 1;
        final int expectedRemainingBytes = 1;

        byte[] source = new byte[sourceLength];
        UnsynchronizedByteArrayInputStream inputStream = new UnsynchronizedByteArrayInputStream(source);
        byte[] destination = new byte[destinationLength];

        int bytesRead = inputStream.read(destination);

        assertEquals(expectedRemainingBytes, inputStream.available());
        assertEquals(expectedBytesRead, bytesRead);
    }
}
