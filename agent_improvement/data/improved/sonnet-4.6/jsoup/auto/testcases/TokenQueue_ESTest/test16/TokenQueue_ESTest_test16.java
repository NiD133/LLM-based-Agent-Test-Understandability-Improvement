package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test16 extends TokenQueue_ESTest_scaffolding {

    /**
     * Verifies that chompBalanced throws IllegalArgumentException when the queue is exhausted
     * before finding a balanced close marker, leaving depth > 0 at end of input.
     *
     * The queue starts with 'o' (which is consumed as the open marker, incrementing depth to 1),
     * but no subsequent unquoted 'o' appears to close it, so the method fails with an error
     * identifying the unconsumed portion of the queue.
     */
    @Test(timeout = 4000)
    public void test_chompBalanced_throwsWhenNoClosingMarkerFound() throws Throwable {
        // The string begins with 'o' (the open marker), followed by content that contains
        // a single-quoted region ('h7]LU) which prevents any inner character from acting
        // as the close marker. The queue is exhausted before depth returns to 0.
        String queueContent = "o`3'h7]LU\"nf[yzQ%n";
        TokenQueue tokenQueue = new TokenQueue(queueContent);

        try {
            tokenQueue.chompBalanced('o', 'o');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
