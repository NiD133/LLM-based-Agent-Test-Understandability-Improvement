package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test28 extends StringUtil_ESTest_scaffolding {

    /**
     * The newline character ('\n', code point 10) should be treated as whitespace.
     */
    @Test(timeout = 4000)
    public void isActuallyWhitespace_returnsTrue_forNewline() throws Throwable {
        int newlineCodePoint = '\n'; // 10

        boolean isWhitespace = StringUtil.isActuallyWhitespace(newlineCodePoint);

        assertTrue("Newline should be recognised as whitespace", isWhitespace);
    }
}
