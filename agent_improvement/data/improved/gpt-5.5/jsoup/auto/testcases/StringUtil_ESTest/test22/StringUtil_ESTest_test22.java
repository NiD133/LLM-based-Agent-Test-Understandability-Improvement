package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test22 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Whitespace-only input is skipped before the null builder would be used.
        StringUtil.appendNormalisedWhitespace((StringBuilder) null, "          ", true);
    }
}
