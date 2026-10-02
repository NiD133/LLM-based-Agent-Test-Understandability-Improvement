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

    // Verifies that available() returns the full byte array length when the stream is created
    // from a 5-byte array with no offset or length restriction.
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        final int bufferSize = 5;
        byte[] inputData = new byte[bufferSize];

        UnsynchronizedByteArrayInputStream.Builder builder = UnsynchronizedByteArrayInputStream.builder();
        builder.setByteArray(inputData);
        UnsynchronizedByteArrayInputStream stream = builder.get();

        assertEquals(bufferSize, stream.available());
    }
}
