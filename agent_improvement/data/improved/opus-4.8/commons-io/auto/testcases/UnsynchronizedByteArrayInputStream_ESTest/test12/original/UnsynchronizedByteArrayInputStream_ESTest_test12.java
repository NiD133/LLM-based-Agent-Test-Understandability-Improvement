package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test12 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder unsynchronizedByteArrayInputStream_Builder0 = UnsynchronizedByteArrayInputStream.builder();
        // Undeclared exception!
        try {
            unsynchronizedByteArrayInputStream_Builder0.setOffset((-2645));
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // offset cannot be negative
            //
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream$Builder", e);
        }
    }
}
