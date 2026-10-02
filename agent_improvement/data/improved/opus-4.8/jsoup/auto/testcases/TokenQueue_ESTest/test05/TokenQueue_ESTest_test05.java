package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test05 extends TokenQueue_ESTest_scaffolding {

    /**
     * A single quote is not a valid CSS identifier character, so consuming a CSS
     * identifier from a queue that starts with one yields an empty string.
     */
    @Test(timeout = 4000)
    public void consumeCssIdentifierReturnsEmptyWhenQueueStartsWithNonIdentChar() throws Throwable {
        TokenQueue queue = new TokenQueue("'");

        String identifier = queue.consumeCssIdentifier();

        assertEquals("", identifier);
    }
}
