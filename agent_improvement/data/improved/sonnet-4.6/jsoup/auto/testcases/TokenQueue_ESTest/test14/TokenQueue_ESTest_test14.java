package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test14 extends TokenQueue_ESTest_scaffolding {

    /**
     * When the input string contains no backslash character, unescape() should
     * return it unchanged (the string has nothing to unescape).
     */
    @Test(timeout = 4000)
    public void test_unescape_returnsInputUnchanged_whenNoBackslashPresent() throws Throwable {
        // String starts with U+001F (Unit Separator control char) but has no backslash
        String inputWithNoBackslash = " 22m6%0O=.8DAypB";

        String result = TokenQueue.unescape(inputWithNoBackslash);

        assertEquals(inputWithNoBackslash, result);
    }
}
