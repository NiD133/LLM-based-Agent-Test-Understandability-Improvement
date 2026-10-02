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
public class NullInputStream_ESTest_test11 extends NullInputStream_ESTest_scaffolding {

    private static final long STREAM_SIZE = -4176L;
    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean THROW_EOF_EXCEPTION = true;
    private static final int READ_LIMIT = -1527;

    // Verifies that calling mark() on a NullInputStream configured without mark support
    // throws UnsupportedOperationException, as defined by the markSupported=false constructor flag.
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream(STREAM_SIZE, MARK_NOT_SUPPORTED, THROW_EOF_EXCEPTION);
        try {
            nullInputStream0.mark(READ_LIMIT);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
