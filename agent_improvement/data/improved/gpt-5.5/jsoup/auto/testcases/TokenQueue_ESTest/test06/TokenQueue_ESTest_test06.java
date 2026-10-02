package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test06 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        TokenQueue queueContainingOnlyWhitespace = new TokenQueue("                  ");

        boolean consumedWhitespace = queueContainingOnlyWhitespace.consumeWhitespace();

        assertTrue(consumedWhitespace);
    }
}
