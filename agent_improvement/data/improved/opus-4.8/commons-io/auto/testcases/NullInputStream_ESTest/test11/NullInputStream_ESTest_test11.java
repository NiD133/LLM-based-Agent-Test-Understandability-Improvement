package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullInputStream_ESTest_test11 extends NullInputStream_ESTest_scaffolding {

    /**
     * When the stream is constructed with mark support disabled, calling
     * {@link NullInputStream#mark(int)} must reject the request by throwing an
     * {@link UnsupportedOperationException} ("mark/reset not supported").
     */
    @Test(timeout = 4000)
    public void markThrowsWhenMarkSupportIsDisabled() throws Throwable {
        final long emulatedSize = -4176L;
        final boolean markSupported = false;
        final boolean throwEofException = true;
        NullInputStream streamWithoutMarkSupport =
                new NullInputStream(emulatedSize, markSupported, throwEofException);

        try {
            streamWithoutMarkSupport.mark(-1527);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // mark/reset not supported
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
