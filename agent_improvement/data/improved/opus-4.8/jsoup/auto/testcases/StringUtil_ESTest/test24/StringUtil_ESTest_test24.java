package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test24 extends StringUtil_ESTest_scaffolding {

    /**
     * A zero-width space (U+200B) is an invisible character, so normalising it
     * away leaves nothing to append to the builder.
     */
    @Test(timeout = 4000)
    public void appendNormalisedWhitespace_dropsZeroWidthSpace() throws Throwable {
        StringBuilder builder = new StringBuilder();
        String zeroWidthSpace = "​";

        StringUtil.appendNormalisedWhitespace(builder, zeroWidthSpace, true);

        assertEquals("", builder.toString());
    }
}
