package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test25 extends StringUtil_ESTest_scaffolding {

    /**
     * Code point 160 is the non-breaking space (&nbsp;). It is not whitespace per the HTML
     * spec, but {@link StringUtil#isActuallyWhitespace(int)} treats it as whitespace.
     */
    @Test(timeout = 4000)
    public void isActuallyWhitespace_treatsNonBreakingSpaceAsWhitespace() throws Throwable {
        int nonBreakingSpaceCodePoint = 160;

        boolean isWhitespace = StringUtil.isActuallyWhitespace(nonBreakingSpaceCodePoint);

        assertTrue(isWhitespace);
    }
}
