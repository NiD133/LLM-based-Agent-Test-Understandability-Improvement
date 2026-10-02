package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test02 extends StringUtil_ESTest_scaffolding {

    /**
     * 'f' is the highest lowercase hexadecimal digit, so isHexDigit should report it as valid.
     */
    @Test(timeout = 4000)
    public void isHexDigit_returnsTrue_forLowercaseF() throws Throwable {
        boolean isHex = StringUtil.isHexDigit('f');

        assertTrue("'f' is a valid hexadecimal digit", isHex);
    }
}
