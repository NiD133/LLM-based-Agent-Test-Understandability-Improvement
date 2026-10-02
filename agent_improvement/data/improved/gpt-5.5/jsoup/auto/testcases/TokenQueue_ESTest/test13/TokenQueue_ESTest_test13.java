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

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        String escapedCharacterClass = "[\\x00-\\x1f]*";
        String unescapedCharacterClass = TokenQueue.unescape(escapedCharacterClass);

        assertEquals("[x00-x1f]*", unescapedCharacterClass);
    }
}
