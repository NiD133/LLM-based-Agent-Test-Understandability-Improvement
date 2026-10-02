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
     * Verifies that consumeCssIdentifier() throws IllegalArgumentException when the
     * queue is empty. The queue is drained first by calling consumeToAny() with no
     * terminator strings, which causes it to consume the entire input.
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("722m6%0O=.8DAypB");

        // Passing no terminators to consumeToAny causes it to consume all remaining input,
        // leaving the queue empty.
        String[] noTerminators = new String[0];
        tokenQueue.consumeToAny(noTerminators);

        // consumeCssIdentifier on an empty queue must throw IllegalArgumentException.
        try {
            tokenQueue.consumeCssIdentifier();
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // CSS identifier expected, but end of input found
            //
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
