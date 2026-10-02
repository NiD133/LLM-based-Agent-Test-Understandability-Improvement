package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.EOFException;
import java.io.IOException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test07 extends NullInputStream_ESTest_scaffolding {

    /**
     * When a NullInputStream is created with mark support disabled,
     * calling reset() must throw an UnsupportedOperationException
     * carrying the "mark/reset not supported" message.
     */
    @Test(timeout = 4000)
    public void resetWithoutMarkSupportThrowsUnsupportedOperation() throws Throwable {
        long emulatedSize = -1184L;
        boolean markSupported = false;
        boolean throwEofException = false;
        NullInputStream streamWithoutMarkSupport =
                new NullInputStream(emulatedSize, markSupported, throwEofException);

        try {
            streamWithoutMarkSupport.reset();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // reset() is unsupported because mark support was disabled;
            // the exception originates from UnsupportedOperationExceptions.
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
