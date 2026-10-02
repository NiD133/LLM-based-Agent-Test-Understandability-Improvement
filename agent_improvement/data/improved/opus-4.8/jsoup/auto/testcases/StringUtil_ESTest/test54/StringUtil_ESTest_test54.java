package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test54 extends StringUtil_ESTest_scaffolding {

    /**
     * normaliseWhitespace collapses a run of trailing spaces into a single space,
     * leaving the leading non-whitespace character untouched.
     */
    @Test(timeout = 4000)
    public void normaliseWhitespaceCollapsesTrailingSpaces() throws Throwable {
        String normalised = StringUtil.normaliseWhitespace("?    ");

        assertEquals("? ", normalised);
    }
}
