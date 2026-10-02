package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test06 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final byte[] sourceAndDestination = new byte[2];
        final int offsetPastEndOfBuffer = (byte) 20;
        final int destinationOffset = (int) (byte) 0;
        final int zeroLengthRead = 0;

        final UnsynchronizedByteArrayInputStream inputStream =
                new UnsynchronizedByteArrayInputStream(sourceAndDestination, offsetPastEndOfBuffer);

        final int bytesRead = inputStream.read(sourceAndDestination, destinationOffset, zeroLengthRead);

        assertEquals(0, inputStream.available());
        assertEquals(0, bytesRead);
    }
}
