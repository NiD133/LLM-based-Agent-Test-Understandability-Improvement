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
     * Verifies that consuming a sequence that does not match the head of the queue
     * throws an IllegalStateException.
     *
     * The queue contains only "p", so attempting to consume the long sequence
     * "=H;zT#>e+|')Uz" must fail immediately.
     */
    @Test(timeout = 4000)
    public void test24_consumeMismatchedSequenceThrowsIllegalStateException() throws Throwable {
        TokenQueue queueWithSingleChar = new TokenQueue("p");

        // Consuming a sequence that doesn't match the queue's content is illegal.
        try {
            queueWithSingleChar.consume("=H;zT#>e+|')Uz");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Queue did not match expected sequence
            //
            verifyException("org.jsoup.parser.TokenQueue", e);
        }
    }
}
