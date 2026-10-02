package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test28 extends StringUtil_ESTest_scaffolding {

    // Unicode code point 10 is the newline character '\n'
    private static final int NEWLINE_CODE_POINT = 10;

    @Test(timeout = 4000)
    public void test28_newlineIsActuallyWhitespace() throws Throwable {
        boolean result = StringUtil.isActuallyWhitespace(NEWLINE_CODE_POINT);
        assertTrue(result);
    }
}
