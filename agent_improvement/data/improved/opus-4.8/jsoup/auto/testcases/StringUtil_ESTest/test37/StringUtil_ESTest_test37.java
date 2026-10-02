package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test37 extends StringUtil_ESTest_scaffolding {

    /**
     * Verifies that {@link StringUtil#startsWithNewline(String)} returns {@code true}
     * when the string's first character is a newline.
     */
    @Test(timeout = 4000)
    public void startsWithNewlineReturnsTrueForNewlineString() throws Throwable {
        boolean startsWithNewline = StringUtil.startsWithNewline("\n");

        assertTrue(startsWithNewline);
    }
}
