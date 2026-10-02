package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test24 extends TokenQueue_ESTest_scaffolding {

    /**
     * consume(String) throws IllegalStateException when the head of the queue
     * does not match the requested sequence. Here the queue holds "p" but we
     * ask it to consume a completely different sequence, so it must fail.
     */
    @Test(timeout = 4000)
    public void consumeThrowsWhenSequenceDoesNotMatch() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("p");

        try {
            tokenQueue.consume("=H;zT#>e+|')Uz");
            fail("Expected IllegalStateException because the queue does not start with the requested sequence");
        } catch (IllegalStateException e) {
            // TokenQueue.consume reports: "Queue did not match expected sequence"
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
