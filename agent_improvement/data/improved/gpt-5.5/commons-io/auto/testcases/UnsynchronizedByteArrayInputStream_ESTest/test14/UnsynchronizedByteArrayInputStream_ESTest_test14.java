package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test14 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        final int negativeLength = -2645;
        UnsynchronizedByteArrayInputStream.Builder builder = UnsynchronizedByteArrayInputStream.builder();

        try {
            builder.setLength(negativeLength);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // length cannot be negative
            //
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream$Builder", e);
        }
    }
}
