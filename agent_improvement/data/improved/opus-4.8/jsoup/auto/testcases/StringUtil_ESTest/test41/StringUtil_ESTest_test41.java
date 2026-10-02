package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test41 extends StringUtil_ESTest_scaffolding {

    /**
     * A string consisting solely of space characters is considered blank,
     * since isBlank treats any all-whitespace string as blank.
     */
    @Test(timeout = 4000)
    public void isBlankReturnsTrueForStringOfOnlySpaces() throws Throwable {
        String onlySpaces = "               ";

        boolean blank = StringUtil.isBlank(onlySpaces);

        assertTrue("A string of only spaces should be considered blank", blank);
    }
}
