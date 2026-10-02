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

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        String queueText = "o`3'h7]LU\"nf[yzQ%n";
        char balancedMarker = 'o';
        TokenQueue tokenQueue = new TokenQueue(queueText);

        try {
            tokenQueue.chompBalanced(balancedMarker, balancedMarker);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
