package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test00 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * A stream built from a byte array should report the full array length as the
     * number of bytes available before anything has been read.
     */
    @Test(timeout = 4000)
    public void availableReturnsFullArrayLengthBeforeReading() throws Throwable {
        final byte[] sourceBytes = new byte[5];

        final UnsynchronizedByteArrayInputStream stream = UnsynchronizedByteArrayInputStream.builder()
                .setByteArray(sourceBytes)
                .get();

        assertEquals(sourceBytes.length, stream.available());
    }
}
