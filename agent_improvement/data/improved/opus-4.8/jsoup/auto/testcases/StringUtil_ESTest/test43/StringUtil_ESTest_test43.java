package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test43 extends StringUtil_ESTest_scaffolding {

    /**
     * A string that contains visible, non-whitespace characters is not blank,
     * so {@link StringUtil#isBlank(String)} should return false.
     */
    @Test(timeout = 4000)
    public void isBlankReturnsFalseForStringWithVisibleCharacters() throws Throwable {
        boolean blank = StringUtil.isBlank("=+fbCpyx:kE");

        assertFalse(blank);
    }
}
