package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test14 extends TokenQueue_ESTest_scaffolding {

    /**
     * {@link TokenQueue#unescape(String)} removes backslash escapes from its input.
     * When the input contains no backslash, the string is returned unchanged
     * (including any other characters, such as the leading control char U+001F here).
     */
    @Test(timeout = 4000)
    public void unescapeReturnsInputUnchangedWhenNoBackslashPresent() throws Throwable {
        String inputWithoutBackslash = " 22m6%0O=.8DAypB";

        String unescaped = TokenQueue.unescape(inputWithoutBackslash);

        assertEquals(inputWithoutBackslash, unescaped);
    }
}
