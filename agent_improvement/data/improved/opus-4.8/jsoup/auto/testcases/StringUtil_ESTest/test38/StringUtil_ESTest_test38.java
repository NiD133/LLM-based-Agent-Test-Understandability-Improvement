package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test38 extends StringUtil_ESTest_scaffolding {

    /**
     * An empty string has no first character, so it cannot start with a newline.
     */
    @Test(timeout = 4000)
    public void startsWithNewlineReturnsFalseForEmptyString() throws Throwable {
        boolean startsWithNewline = StringUtil.startsWithNewline("");

        assertFalse(startsWithNewline);
    }
}
