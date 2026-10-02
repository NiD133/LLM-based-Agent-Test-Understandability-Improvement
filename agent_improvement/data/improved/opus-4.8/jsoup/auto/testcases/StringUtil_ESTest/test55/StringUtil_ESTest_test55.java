package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test55 extends StringUtil_ESTest_scaffolding {

    /**
     * Normalising the whitespace of "? " (a single trailing space, no leading whitespace
     * to strip) leaves the text unchanged: the non-whitespace "?" is kept and the single
     * trailing space is preserved.
     */
    @Test(timeout = 4000)
    public void appendNormalisedWhitespace_withSingleTrailingSpace_keepsTextUnchanged() throws Throwable {
        StringBuilder builder = StringUtil.borrowBuilder();

        StringUtil.appendNormalisedWhitespace(builder, "? ", true);

        assertEquals("? ", builder.toString());
    }
}
