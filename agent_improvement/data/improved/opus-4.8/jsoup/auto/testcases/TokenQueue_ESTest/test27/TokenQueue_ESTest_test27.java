package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test27 extends TokenQueue_ESTest_scaffolding {

    /**
     * matchChomp(char) should return false when the next character in the queue
     * does not match the requested character. Here the queue starts with a single
     * quote, so chomping for 'r' must not match.
     */
    @Test(timeout = 4000)
    public void matchChompReturnsFalseWhenNextCharDoesNotMatch() throws Throwable {
        TokenQueue queue = new TokenQueue("'");

        boolean matched = queue.matchChomp('r');

        assertFalse(matched);
    }
}
