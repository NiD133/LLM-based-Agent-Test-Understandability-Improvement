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
     * Verifies that calling reset() on a NullInputStream created without mark support
     * throws UnsupportedOperationException with the expected "mark/reset not supported" message.
     */
    @Test(timeout = 4000)
    public void test_reset_throwsUnsupportedOperationException_whenMarkNotSupported() throws Throwable {
        boolean markSupported = false;
        boolean throwEofException = false;
        NullInputStream streamWithoutMarkSupport = new NullInputStream((-1184L), markSupported, throwEofException);

        try {
            streamWithoutMarkSupport.reset();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // mark/reset not supported
            //
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
