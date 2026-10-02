package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test13 extends TokenQueue_ESTest_scaffolding {

    // unescape() strips a single leading backslash from each escaped character,
    // so "\x00" becomes "x00" and "\x1f" becomes "x1f".
    private static final String REGEX_CHAR_CLASS_WITH_ESCAPES = "[\\x00-\\x1f]*";
    private static final String EXPECTED_UNESCAPED_RESULT     = "[x00-x1f]*";

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        String unescapedCharClass = TokenQueue.unescape(REGEX_CHAR_CLASS_WITH_ESCAPES);
        assertEquals(EXPECTED_UNESCAPED_RESULT, unescapedCharClass);
    }
}
