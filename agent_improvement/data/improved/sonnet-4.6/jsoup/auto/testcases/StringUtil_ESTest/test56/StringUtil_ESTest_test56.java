package org.jsoup.internal;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test56 extends StringUtil_ESTest_scaffolding {

    // Verifies that StringUtil can be instantiated without throwing any exception.
    @Test(timeout = 4000)
    public void defaultConstructorDoesNotThrow() throws Throwable {
        StringUtil stringUtil0 = new StringUtil();
    }
}
