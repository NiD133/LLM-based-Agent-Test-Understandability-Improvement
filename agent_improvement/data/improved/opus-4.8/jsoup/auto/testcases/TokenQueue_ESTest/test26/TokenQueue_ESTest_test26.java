package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test26 extends TokenQueue_ESTest_scaffolding {

    /**
     * matchChomp(char) should report true when the queue's first character matches
     * the supplied character (case-sensitively), consuming it off the queue.
     */
    @Test(timeout = 4000)
    public void matchChompConsumesMatchingFirstCharacter() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("722m6%0O=.8DAypB");

        boolean matchedAndConsumed = tokenQueue.matchChomp('7');

        assertTrue("first character '7' should match and be consumed", matchedAndConsumed);
    }
}
