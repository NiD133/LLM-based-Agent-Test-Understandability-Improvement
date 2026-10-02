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
     * When mark/reset is not supported, calling {@link NullReader#reset()}
     * must throw an {@link UnsupportedOperationException}.
     */
    @Test(timeout = 4000)
    public void resetThrowsWhenMarkNotSupported() throws Throwable {
        // markSupported = false, so reset() is not allowed.
        NullReader readerWithoutMarkSupport = new NullReader(0L, false, false);

        try {
            readerWithoutMarkSupport.reset();
            fail("Expected an UnsupportedOperationException because mark/reset is not supported");
        } catch (UnsupportedOperationException e) {
            // The exception originates from the UnsupportedOperationExceptions factory.
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
