package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test21 extends TokenQueue_ESTest_scaffolding {

    /**
     * consumeToAny stops as soon as the queue head matches any terminator.
     * When the queue starts with the terminator itself, nothing is consumed
     * and the result is an empty string.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        String queueContent = "p h7u#";
        TokenQueue tokenQueue = new TokenQueue(queueContent);

        // Use the full queue content as the only terminator.
        // Because the queue starts with this terminator, consumeToAny should
        // stop immediately and return an empty string.
        String[] terminators = new String[] { queueContent };
        String consumed = tokenQueue.consumeToAny(terminators);

        assertEquals("", consumed);
    }
}
