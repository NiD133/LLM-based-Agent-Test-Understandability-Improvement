package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test10 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        byte[] oneByteBuffer = new byte[1];
        int negativeOffset = -9;
        int negativeLength = -9;
        UnsynchronizedByteArrayInputStream stream = null;

        try {
            stream = new UnsynchronizedByteArrayInputStream(oneByteBuffer, negativeOffset, negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // offset cannot be negative
            //
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream", e);
        }
    }
}
