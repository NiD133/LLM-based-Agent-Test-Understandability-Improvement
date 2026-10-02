package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test04 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_isHexDigit_returnsTrueForDecimalDigit() throws Throwable {
        boolean result = StringUtil.isHexDigit('1');
        assertTrue("'1' is a valid hex digit (0-9 are all valid hex digits)", result);
    }
}
