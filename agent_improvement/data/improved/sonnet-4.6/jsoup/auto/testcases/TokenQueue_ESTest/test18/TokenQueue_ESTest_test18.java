package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test18 extends TokenQueue_ESTest_scaffolding {

    /**
     * When open and close delimiters are the same character (single quote),
     * a queue containing only that one character cannot form a balanced pair.
     * chompBalanced should throw IllegalArgumentException because the queue is
     * exhausted before the closing delimiter is found.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Queue contains a single lone quote — no matching close delimiter exists
        TokenQueue tokenQueue0 = new TokenQueue("'");

        try {
            tokenQueue0.chompBalanced('\'', '\'');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Did not find balanced marker at ''"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
