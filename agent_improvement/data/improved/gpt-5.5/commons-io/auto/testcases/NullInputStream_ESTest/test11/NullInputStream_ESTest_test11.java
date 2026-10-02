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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        NullInputStream streamWithoutMarkSupport = new NullInputStream((-4176L), false, true);

        try {
            streamWithoutMarkSupport.mark((-1527));
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // mark(int) must reject calls when the stream was created with mark support disabled.
            verifyException("org.apache.commons.io.input.UnsupportedOperationExceptions", e);
        }
    }
}
