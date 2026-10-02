package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test01 extends TokenQueue_ESTest_scaffolding {

    /**
     * A plain tag name is a valid CSS element selector, so consuming the element
     * selector from a queue holding only "n" should return the whole string "n".
     */
    @Test(timeout = 4000)
    public void consumeElementSelectorReturnsPlainTagName() throws Throwable {
        TokenQueue queue = new TokenQueue("n");

        String selector = queue.consumeElementSelector();

        assertEquals("n", selector);
    }
}
