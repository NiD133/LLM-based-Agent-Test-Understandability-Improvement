package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test03 extends StringUtil_ESTest_scaffolding {

    /**
     * 'w' is a letter outside the hex-digit ranges (0-9, a-f, A-F),
     * so {@link StringUtil#isHexDigit(char)} should report it as not a hex digit.
     */
    @Test(timeout = 4000)
    public void isHexDigit_withNonHexLetter_returnsFalse() throws Throwable {
        boolean isHexDigit = StringUtil.isHexDigit('w');

        assertFalse(isHexDigit);
    }
}
