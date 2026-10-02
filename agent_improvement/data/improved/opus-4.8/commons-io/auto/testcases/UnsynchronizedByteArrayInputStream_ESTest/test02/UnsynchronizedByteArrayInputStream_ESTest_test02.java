package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnsynchronizedByteArrayInputStream_ESTest_test02 extends UnsynchronizedByteArrayInputStream_ESTest_scaffolding {

    /**
     * Verifies that {@link UnsynchronizedByteArrayInputStream#skip(long)} rejects a negative
     * number of bytes, because skipping backward is not supported and must raise an
     * {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void skipWithNegativeCountThrowsIllegalArgumentException() throws Throwable {
        byte[] buffer = new byte[6];
        UnsynchronizedByteArrayInputStream inputStream =
                new UnsynchronizedByteArrayInputStream(buffer, 304);

        try {
            inputStream.skip(-1634L);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Skipping backward is not supported.
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream", e);
        }
    }
}
