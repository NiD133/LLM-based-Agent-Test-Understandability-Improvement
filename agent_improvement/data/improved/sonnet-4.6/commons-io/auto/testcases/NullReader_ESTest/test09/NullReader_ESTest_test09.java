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
     * A NullReader constructed with markSupported=false should throw
     * UnsupportedOperationException when mark() is called.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        final long readerSize = 2857L;
        final boolean markNotSupported = false;
        final boolean doNotThrowEof = false;

        NullReader readerWithoutMarkSupport = new NullReader(readerSize, markNotSupported, doNotThrowEof);

        try {
            readerWithoutMarkSupport.mark(3);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            //
            // mark/reset not supported
            //
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
