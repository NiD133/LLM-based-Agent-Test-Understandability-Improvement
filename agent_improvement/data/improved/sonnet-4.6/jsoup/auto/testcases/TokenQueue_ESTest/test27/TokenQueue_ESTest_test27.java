package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test27 extends TokenQueue_ESTest_scaffolding {

    /**
     * matchChomp(char) should return false and leave the queue unchanged
     * when the first character in the queue does not match the given character.
     */
    @Test(timeout = 4000)
    public void test_matchChomp_returnsFalse_whenFirstCharDoesNotMatch() throws Throwable {
        // Queue contains only a single-quote character; 'r' is not present at the head
        TokenQueue tokenQueue = new TokenQueue("'");

        boolean matched = tokenQueue.matchChomp('r');

        assertFalse("matchChomp should return false when the head character does not match", matched);
    }
}
