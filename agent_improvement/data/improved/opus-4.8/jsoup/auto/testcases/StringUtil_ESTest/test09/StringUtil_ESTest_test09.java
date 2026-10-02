package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test09 extends StringUtil_ESTest_scaffolding {

    /**
     * The digit character '1' is not an ASCII letter (a-z or A-Z),
     * so {@link StringUtil#isAsciiLetter(char)} should return false.
     */
    @Test(timeout = 4000)
    public void isAsciiLetter_withDigitChar_returnsFalse() throws Throwable {
        boolean isLetter = StringUtil.isAsciiLetter('1');

        assertFalse(isLetter);
    }
}
