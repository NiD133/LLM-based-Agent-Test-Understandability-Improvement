package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test17 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        final String selectorWithUnbalancedQuote = "_Tw\"FhC|($XN0!Uv;9";
        final char doubleQuote = '\"';

        TokenQueue tokenQueue = new TokenQueue(selectorWithUnbalancedQuote);
        tokenQueue.consumeElementSelector();

        try {
            tokenQueue.chompBalanced(doubleQuote, doubleQuote);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            verifyException("org.jsoup.helper.Validate", expected);
        }
    }
}
