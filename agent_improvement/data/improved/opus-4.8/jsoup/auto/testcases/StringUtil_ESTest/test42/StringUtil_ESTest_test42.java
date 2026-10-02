package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test42 extends StringUtil_ESTest_scaffolding {

    /**
     * An empty string is considered blank, since blank means null, empty, or whitespace-only.
     */
    @Test(timeout = 4000)
    public void isBlankReturnsTrueForEmptyString() throws Throwable {
        boolean isBlank = StringUtil.isBlank("");

        assertTrue("An empty string should be reported as blank", isBlank);
    }
}
