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

    /**
     * The constructor rejects a negative offset by throwing an
     * IllegalArgumentException ("offset cannot be negative").
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeOffset() throws Throwable {
        byte[] data = new byte[1];
        int negativeOffset = -9;
        int length = -9;

        try {
            new UnsynchronizedByteArrayInputStream(data, negativeOffset, length);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // offset cannot be negative
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream", e);
        }
    }
}
