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

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final long streamSize = -1184L;
        final boolean markSupported = false;
        final boolean throwEofException = false;
        final NullInputStream inputStream = new NullInputStream(streamSize, markSupported, throwEofException);

        try {
            inputStream.reset();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException exception) {
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", exception);
        }
    }
}
