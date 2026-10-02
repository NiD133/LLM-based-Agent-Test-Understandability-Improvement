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

    /**
     * Verifies that {@link UnsynchronizedByteArrayInputStream.Builder#setLength(int)}
     * rejects a negative length by throwing an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void setLengthWithNegativeValueThrowsIllegalArgumentException() throws Throwable {
        UnsynchronizedByteArrayInputStream.Builder builder = UnsynchronizedByteArrayInputStream.builder();

        try {
            builder.setLength(-2645);
            fail("Expected IllegalArgumentException because length cannot be negative");
        } catch (IllegalArgumentException e) {
            // Builder.setLength rejects negative lengths with message "length cannot be negative"
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream$Builder", e);
        }
    }
}
