package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test08 extends StringUtil_ESTest_scaffolding {

    /**
     * A lowercase ASCII letter such as 'f' should be recognised as an ASCII letter.
     */
    @Test(timeout = 4000)
    public void isAsciiLetter_lowercaseLetter_returnsTrue() throws Throwable {
        boolean isLetter = StringUtil.isAsciiLetter('f');

        assertTrue("'f' is a lowercase ASCII letter", isLetter);
    }
}
