package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test15 extends StringUtil_ESTest_scaffolding {

    /**
     * An empty string contains no characters outside the ASCII range,
     * so {@link StringUtil#isAscii(String)} should consider it ASCII.
     */
    @Test(timeout = 4000)
    public void emptyStringIsAscii() throws Throwable {
        boolean isAscii = StringUtil.isAscii("");

        assertTrue(isAscii);
    }
}
