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
public class NullInputStream_ESTest_test05 extends NullInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        NullInputStream nullInputStream0 = NullInputStream.INSTANCE;
        try {
            nullInputStream0.INSTANCE.reset();
            fail("Expecting exception: IOException");
        } catch (IOException e) {
            //
            // No position has been marked
            //
            verifyException("org.apache.commons.io.input.NullInputStream", e);
        }
    }
}
