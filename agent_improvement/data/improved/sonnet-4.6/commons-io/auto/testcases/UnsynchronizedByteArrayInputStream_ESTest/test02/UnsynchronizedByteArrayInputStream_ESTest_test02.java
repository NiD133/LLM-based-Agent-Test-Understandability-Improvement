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
     * Verifies that calling skip() with a negative value throws IllegalArgumentException,
     * because skipping backward in the stream is not supported.
     */
    @Test(timeout = 4000)
    public void test_skip_negativeValue_throwsIllegalArgumentException() throws Throwable {
        byte[] buffer = new byte[6];
        // Offset 304 exceeds buffer length (6), so the stream starts at the end (eod = 0, offset = 0)
        UnsynchronizedByteArrayInputStream stream = new UnsynchronizedByteArrayInputStream(buffer, 304);

        try {
            stream.skip(-1634L);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Skipping backward is not supported"
            verifyException("org.apache.commons.io.input.UnsynchronizedByteArrayInputStream", e);
        }
    }
}
