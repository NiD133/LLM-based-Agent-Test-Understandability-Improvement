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
public class NullInputStream_ESTest_test06 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        NullInputStream nullInputStream0 = new NullInputStream(2147483647L);
        nullInputStream0.mark((-645));
        try {
            nullInputStream0.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // Marked position [0] is no longer valid - passed the read limit [-645]
            //
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
