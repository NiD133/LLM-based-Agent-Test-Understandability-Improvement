package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test07 extends StringUtil_ESTest_scaffolding {

    /**
     * isAsciiLetter should return false for a non-letter character.
     * Here we use the DEL control character (code point 127), which is
     * outside both the 'a'-'z' and 'A'-'Z' ranges.
     */
    @Test(timeout = 4000)
    public void isAsciiLetter_returnsFalse_forDelControlCharacter() throws Throwable {
        char delControlChar = (char) 127;

        boolean isLetter = StringUtil.isAsciiLetter(delControlChar);

        assertFalse(isLetter);
    }
}
