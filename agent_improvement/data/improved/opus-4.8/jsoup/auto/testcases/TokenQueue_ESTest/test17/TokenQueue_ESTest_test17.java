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

    /**
     * chompBalanced throws IllegalArgumentException when the queue contains an
     * opening marker but no matching closing marker, leaving the input unbalanced.
     *
     * Here the leading element selector "_Tw" is consumed first, leaving
     * "\"FhC|($XN0!Uv;9" on the queue. chompBalanced('"', '"') consumes the
     * opening quote but never finds a closing quote, so it fails via
     * Validate.fail with a "Did not find balanced marker" message.
     */
    @Test(timeout = 4000)
    public void chompBalancedWithoutClosingMarkerThrows() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("_Tw\"FhC|($XN0!Uv;9");

        // Consume the leading element selector "_Tw"; the queue now starts at the quote.
        tokenQueue.consumeElementSelector();

        try {
            tokenQueue.chompBalanced('\"', '\"');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Did not find balanced marker at 'FhC|($XN0!Uv;9'
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
