package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test04 extends StringUtil_ESTest_scaffolding {

    /**
     * '1' is an ASCII digit, and every digit is a valid hex digit,
     * so {@link StringUtil#isHexDigit(char)} should return true.
     */
    @Test(timeout = 4000)
    public void isHexDigitReturnsTrueForDigit() throws Throwable {
        boolean isHexDigit = StringUtil.isHexDigit('1');

        assertTrue("'1' should be recognised as a hex digit", isHexDigit);
    }
}
