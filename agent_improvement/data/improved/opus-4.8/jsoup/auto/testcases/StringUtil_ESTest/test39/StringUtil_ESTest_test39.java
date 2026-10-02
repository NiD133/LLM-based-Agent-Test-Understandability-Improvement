package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test39 extends StringUtil_ESTest_scaffolding {

    /**
     * A string whose first character is not '\n' should not be reported as
     * starting with a newline. Here the input is a single zero-width space
     * (U+200B), which is not a newline.
     */
    @Test(timeout = 4000)
    public void startsWithNewline_returnsFalse_whenFirstCharIsNotNewline() throws Throwable {
        String zeroWidthSpace = "\u200B"; // U+200B ZERO WIDTH SPACE

        boolean startsWithNewline = StringUtil.startsWithNewline(zeroWidthSpace);

        assertFalse(startsWithNewline);
    }
}
