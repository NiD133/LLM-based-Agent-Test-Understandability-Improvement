package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test23 extends TokenQueue_ESTest_scaffolding {

    /**
     * consumeCssIdentifier() must reject an exhausted queue: once every character has
     * been consumed, asking for a CSS identifier throws IllegalArgumentException with
     * the message "CSS identifier expected, but end of input found".
     */
    @Test(timeout = 4000)
    public void consumeCssIdentifierOnEmptyQueueThrows() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("722m6%0O=.8DAypB");

        // No terminators are supplied, so consumeToAny consumes the queue to its end,
        // leaving it empty.
        String[] noTerminators = new String[0];
        tokenQueue.consumeToAny(noTerminators);

        try {
            tokenQueue.consumeCssIdentifier();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // CSS identifier expected, but end of input found
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
