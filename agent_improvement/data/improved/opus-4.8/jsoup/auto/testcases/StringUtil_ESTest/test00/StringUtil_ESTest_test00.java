package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test00 extends StringUtil_ESTest_scaffolding {

    /**
     * 'C' is an uppercase letter in the A-F range, so it is a valid hexadecimal digit.
     */
    @Test(timeout = 4000)
    public void isHexDigit_returnsTrue_forUppercaseHexLetter() throws Throwable {
        boolean isHexDigit = StringUtil.isHexDigit('C');

        assertTrue(isHexDigit);
    }
}
