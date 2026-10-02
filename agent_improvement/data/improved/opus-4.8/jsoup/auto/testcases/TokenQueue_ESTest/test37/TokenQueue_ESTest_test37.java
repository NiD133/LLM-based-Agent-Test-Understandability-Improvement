package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test37 extends TokenQueue_ESTest_scaffolding {

    /**
     * matches(char) on an empty queue should report no match, since there is
     * no next character to compare against.
     */
    @Test(timeout = 4000)
    public void matchesCharOnEmptyQueueReturnsFalse() throws Throwable {
        TokenQueue emptyQueue = new TokenQueue("");

        boolean matched = emptyQueue.matches(''); // DEL control character

        assertFalse(matched);
    }
}
