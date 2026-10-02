package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test21 extends TokenQueue_ESTest_scaffolding {

    /**
     * When the queue head already matches one of the terminator sequences,
     * consumeToAny stops immediately and returns an empty string, leaving the
     * terminator on the queue.
     */
    @Test(timeout = 4000)
    public void consumeToAnyReturnsEmptyWhenHeadMatchesTerminator() throws Throwable {
        String content = "p h7u#";
        TokenQueue tokenQueue = new TokenQueue(content);

        // The only terminator is the full content, so the queue matches it at position 0.
        String consumed = tokenQueue.consumeToAny(content);

        assertEquals("", consumed);
    }
}
