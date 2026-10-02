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
public class NullReader_ESTest_test09 extends NullReader_ESTest_scaffolding {

    /**
     * When a NullReader is created with mark support disabled, calling mark()
     * must throw an UnsupportedOperationException ("mark/reset not supported").
     */
    @Test(timeout = 4000)
    public void markThrowsWhenMarkNotSupported() throws Throwable {
        // markSupported = false, so mark() is not allowed.
        NullReader readerWithoutMarkSupport = new NullReader(2857L, false, false);

        try {
            readerWithoutMarkSupport.mark(3);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // mark/reset not supported
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
