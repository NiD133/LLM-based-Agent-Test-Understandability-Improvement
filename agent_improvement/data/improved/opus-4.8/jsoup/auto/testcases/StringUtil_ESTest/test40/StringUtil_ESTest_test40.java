package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test40 extends StringUtil_ESTest_scaffolding {

    /**
     * A null input is not considered to start with a newline, so
     * {@link StringUtil#startsWithNewline(String)} returns false.
     */
    @Test(timeout = 4000)
    public void startsWithNewline_returnsFalse_forNullInput() throws Throwable {
        boolean startsWithNewline = StringUtil.startsWithNewline((String) null);

        assertFalse(startsWithNewline);
    }
}
