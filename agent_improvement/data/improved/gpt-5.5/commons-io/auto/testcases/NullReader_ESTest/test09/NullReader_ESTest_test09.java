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

    private static final long READER_SIZE = 2857L;
    private static final boolean MARK_NOT_SUPPORTED = false;
    private static final boolean DO_NOT_THROW_EOF_EXCEPTION = false;
    private static final int READ_AHEAD_LIMIT = 3;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        NullReader readerWithoutMarkSupport = new NullReader(
                READER_SIZE,
                MARK_NOT_SUPPORTED,
                DO_NOT_THROW_EOF_EXCEPTION);

        try {
            readerWithoutMarkSupport.mark(READ_AHEAD_LIMIT);
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
