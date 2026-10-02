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

    /**
     * Builder.setOffset rejects a negative offset by throwing
     * IllegalArgumentException with the message "offset cannot be negative".
     */
    @Test(timeout = 4000)
    public void setOffsetWithNegativeValueThrowsIllegalArgumentException() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder builder = UnsynchronizedByteArrayInputStream.builder();
        int negativeOffset = -2645;

        try {
            builder.setOffset(negativeOffset);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // offset cannot be negative
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream$Builder", e);
        }
    }
}
