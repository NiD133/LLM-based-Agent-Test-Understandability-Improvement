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

    // Input: "_Tw\"FhC|($XN0!Uv;9"
    // consumeElementSelector() consumes "_Tw" (valid element selector chars), leaving `"FhC|($XN0!Uv;9`
    // chompBalanced('"', '"') then attempts to find a balanced pair of double-quotes,
    // but the queue has no closing '"', so an IllegalArgumentException is expected.
    @Test(timeout = 4000)
    public void test17() throws Throwable {
        // The queue starts with an element selector prefix followed by an unbalanced double-quoted section
        TokenQueue tokenQueue0 = new TokenQueue("_Tw\"FhC|($XN0!Uv;9");

        // Consume the leading element selector "_Tw"; the remaining queue is: "FhC|($XN0!Uv;9
        tokenQueue0.consumeElementSelector();

        // Attempting to chomp a balanced double-quote pair should fail because there is no closing '"'
        try {
            tokenQueue0.chompBalanced('"', '"');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The error message names the unbalanced content found before the queue was exhausted
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
