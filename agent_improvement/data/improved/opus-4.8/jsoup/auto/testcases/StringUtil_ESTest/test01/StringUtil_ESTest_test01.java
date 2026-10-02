package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test01 extends StringUtil_ESTest_scaffolding {

    /**
     * A control character (U+001D, the "Group Separator") is not a hex digit,
     * so {@link StringUtil#isHexDigit(char)} should report it as false.
     */
    @Test(timeout = 4000)
    public void isHexDigitReturnsFalseForControlCharacter() throws Throwable {
        char controlChar = (char) 0x1D;

        boolean isHexDigit = StringUtil.isHexDigit(controlChar);

        assertFalse(isHexDigit);
    }
}
