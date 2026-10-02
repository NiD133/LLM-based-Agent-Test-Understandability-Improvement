package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test02 extends TokenQueue_ESTest_scaffolding {

    /**
     * When chompBalanced is asked to balance markers that never appear in the
     * input, the single-quoted content is treated as a quoted literal and the
     * whole queue is consumed and returned. After that the queue is exhausted,
     * so consumeElementSelector yields an empty string that differs from the
     * chomped content.
     */
    @Test(timeout = 4000)
    public void chompBalancedConsumesQuotedLiteralThenSelectorIsEmpty() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("'");

        // The open ')' and close '$' markers are absent; the lone single quote
        // is kept as quoted content and returned verbatim.
        String chompedContent = tokenQueue.chompBalanced(')', '$');
        assertEquals("'", chompedContent);

        // The queue is now empty, so the element selector comes back empty and
        // is therefore not equal to the previously chomped content.
        String elementSelector = tokenQueue.consumeElementSelector();
        assertFalse(elementSelector.equals(chompedContent));
    }
}
