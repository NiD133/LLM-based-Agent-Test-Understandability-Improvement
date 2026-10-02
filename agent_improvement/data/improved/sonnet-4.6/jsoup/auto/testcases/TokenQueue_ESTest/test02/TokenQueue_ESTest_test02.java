package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test02 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that chompBalanced on a queue containing only a single-quote
     * returns the entire queue contents (because the quote char is treated as
     * content, not as an unmatched open/close delimiter), and that the queue
     * is empty afterwards so consumeElementSelector returns a different string.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Queue contains a single single-quote character
        TokenQueue tokenQueue = new TokenQueue("'");

        // chompBalanced with open=')' and close='$': the "'" is neither delimiter,
        // so it is accumulated as quoted content and returned as-is.
        String balancedResult = tokenQueue.chompBalanced(')', '$');
        assertEquals("chompBalanced should return the single-quote that was in the queue",
                "'", balancedResult);

        // The queue is now empty; consumeElementSelector returns an empty string,
        // which is not equal to the previously returned single-quote string.
        String elementSelector = tokenQueue.consumeElementSelector();
        assertFalse("consumeElementSelector on an empty queue should not equal the earlier balanced result",
                elementSelector.equals((Object) balancedResult));
    }
}
