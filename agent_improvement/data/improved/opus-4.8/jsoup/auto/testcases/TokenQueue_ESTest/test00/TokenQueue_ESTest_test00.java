package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test00 extends TokenQueue_ESTest_scaffolding {

    /**
     * consumeElementSelector() reads leading tag/selector characters and stops at
     * the first character that is not allowed in an element selector. For the input
     * "8)cpsx*", parsing stops at ')', so only the leading "8" is consumed.
     */
    @Test(timeout = 4000)
    public void consumeElementSelectorStopsAtFirstDisallowedCharacter() throws Throwable {
        TokenQueue queue = new TokenQueue("8)cpsx*");

        String selector = queue.consumeElementSelector();

        assertEquals("8", selector);
    }
}
