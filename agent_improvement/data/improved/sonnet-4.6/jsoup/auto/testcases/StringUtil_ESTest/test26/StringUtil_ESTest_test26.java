package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test26 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void carriageReturnIsActuallyWhitespace() throws Throwable {
        boolean result = StringUtil.isActuallyWhitespace('\r'); // '\r' has code point 13
        assertTrue(result);
    }
}
