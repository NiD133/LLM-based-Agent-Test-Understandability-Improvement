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
     * chompBalanced opens the balanced region on the first 'o' but never finds a
     * matching closer (the input has no second 'o'), so it runs out of input while
     * still unbalanced and Validate.fail throws an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void chompBalanced_whenCloserNeverFound_throwsIllegalArgumentException() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("o`3'h7]LU\"nf[yzQ%n");

        try {
            tokenQueue.chompBalanced('o', 'o');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.fail reports: Did not find balanced marker at '`3'h7]LU"nf[yzQ%n'
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
