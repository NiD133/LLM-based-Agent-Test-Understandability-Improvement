package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class IOCase_ESTest_test25 extends IOCase_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        final String enumBackingArrayFieldName = "$VALUES";

        try {
            IOCase.forName(enumBackingArrayFieldName);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.io.IOCase", e);
        }
    }
}
