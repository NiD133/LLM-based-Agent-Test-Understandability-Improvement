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
     * Verifies that {@link StringUtil#isAsciiLetter(char)} returns true for an
     * uppercase ASCII letter ('O').
     */
    @Test(timeout = 4000)
    public void isAsciiLetter_uppercaseLetter_returnsTrue() throws Throwable {
        boolean isLetter = StringUtil.isAsciiLetter('O');

        assertTrue("'O' is an uppercase ASCII letter", isLetter);
    }
}
