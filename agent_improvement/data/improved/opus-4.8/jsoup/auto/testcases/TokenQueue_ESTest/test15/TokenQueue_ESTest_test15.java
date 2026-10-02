package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test15 extends TokenQueue_ESTest_scaffolding {

    /**
     * chompBalanced returns the content between the opening and closing markers,
     * excluding the markers themselves. Here the queue is "(1upvc8O": starting at
     * the opening '(' and stopping at the closing 'v', so the captured content is "1up".
     */
    @Test(timeout = 4000)
    public void chompBalancedReturnsContentBetweenOpenAndCloseMarkers() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("(1upvc8O");

        String chomped = tokenQueue.chompBalanced('(', 'v');

        assertEquals("1up", chomped);
    }
}
