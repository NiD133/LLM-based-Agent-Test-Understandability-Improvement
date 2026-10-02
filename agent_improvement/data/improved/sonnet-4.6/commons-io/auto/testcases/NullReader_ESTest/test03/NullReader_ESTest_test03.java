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
public class NullReader_ESTest_test03 extends NullReader_ESTest_scaffolding {

    /**
     * Verifies that calling reset() on a NullReader configured without mark support
     * throws UnsupportedOperationException.
     *
     * NullReader(size=0, markSupported=false, throwEofException=false):
     *   - markSupported=false means mark/reset operations are not available.
     * Calling reset() without mark support must throw UnsupportedOperationException.
     */
    @Test(timeout = 4000)
    public void test03_resetWithoutMarkSupportThrowsUnsupportedOperationException() throws Throwable {
        NullReader readerWithoutMarkSupport = new NullReader(0L, false, false);

        try {
            readerWithoutMarkSupport.reset();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // mark/reset not supported
            //
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
