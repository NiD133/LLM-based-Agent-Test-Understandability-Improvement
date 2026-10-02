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
    public void test06_consumeWhitespace_returnsTrueWhenQueueContainsOnlySpaces() throws Throwable {
        // A queue containing only whitespace characters should report that whitespace was consumed
        TokenQueue whitespaceOnlyQueue = new TokenQueue("                  ");
        boolean whitespaceWasConsumed = whitespaceOnlyQueue.consumeWhitespace();
        assertTrue(whitespaceWasConsumed);
    }
}
