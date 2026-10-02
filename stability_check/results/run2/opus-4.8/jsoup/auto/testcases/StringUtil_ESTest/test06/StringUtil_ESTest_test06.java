package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test06 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that an uppercase ASCII letter is recognised as a letter.
     */
    @Test(timeout = 4000)
    public void isAsciiLetter_returnsTrue_forUppercaseLetter() throws Throwable {
        boolean isLetter = StringUtil.isAsciiLetter('O');

        assertTrue("Uppercase 'O' should be recognised as an ASCII letter", isLetter);
    }
}
