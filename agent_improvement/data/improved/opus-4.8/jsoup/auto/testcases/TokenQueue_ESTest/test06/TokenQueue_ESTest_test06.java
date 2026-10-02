package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test06 extends TokenQueue_ESTest_scaffolding {

    /**
     * consumeWhitespace() should return true when the queue begins with
     * whitespace, indicating that at least one whitespace character was consumed.
     */
    @Test(timeout = 4000)
    public void consumeWhitespaceReturnsTrueWhenQueueStartsWithWhitespace() throws Throwable {
        TokenQueue queueOfSpaces = new TokenQueue("                  ");

        boolean consumedWhitespace = queueOfSpaces.consumeWhitespace();

        assertTrue(consumedWhitespace);
    }
}
