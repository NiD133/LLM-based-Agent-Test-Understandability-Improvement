package org.jsoup.internal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test56 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that the StringUtil default constructor can be invoked without
     * throwing. StringUtil is a utility class exposing only static helpers, so
     * instantiating it has no observable side effects; this simply exercises
     * the implicit no-arg constructor.
     */
    @Test(timeout = 4000)
    public void test56() throws Throwable {
        StringUtil stringUtil = new StringUtil();
    }
}
