package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test13 extends TokenQueue_ESTest_scaffolding {

    /**
     * {@link TokenQueue#unescape(String)} strips single backslash escape characters,
     * keeping the character that follows each one. Here every "\x" becomes just "x".
     */
    @Test(timeout = 4000)
    public void unescapeRemovesSingleBackslashes() throws Throwable {
        String unescaped = TokenQueue.unescape("[\\x00-\\x1f]*");

        assertEquals("[x00-x1f]*", unescaped);
    }
}
